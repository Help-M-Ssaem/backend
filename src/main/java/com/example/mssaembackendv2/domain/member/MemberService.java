package com.example.mssaembackendv2.domain.member;

import com.example.mssaembackendv2.domain.badge.Badge;
import com.example.mssaembackendv2.domain.badge.BadgeEnum;
import com.example.mssaembackendv2.domain.badge.BadgeRepository;
import com.example.mssaembackendv2.domain.badge.BadgeService;
import com.example.mssaembackendv2.domain.board.BoardService;
import com.example.mssaembackendv2.domain.discussion.DiscussionService;
import com.example.mssaembackendv2.domain.evaluation.EvaluationService;
import com.example.mssaembackendv2.domain.member.dto.MemberRequestDto.ModifyProfile;
import com.example.mssaembackendv2.domain.member.dto.MemberRequestDto.CheckNickName;
import com.example.mssaembackendv2.domain.member.dto.MemberRequestDto.RegisterMember;
import com.example.mssaembackendv2.domain.member.dto.MemberRequestDto.SocialLoginToken;
import com.example.mssaembackendv2.domain.member.dto.MemberResponseDto;
import com.example.mssaembackendv2.domain.member.dto.MemberResponseDto.MemberInfo;
import com.example.mssaembackendv2.domain.member.dto.MemberResponseDto.MemberSimpleInfo;
import com.example.mssaembackendv2.domain.member.dto.MemberResponseDto.MemberProfileInfo;
import com.example.mssaembackendv2.domain.member.dto.MemberResponseDto.CheckNickNameRes;
import com.example.mssaembackendv2.domain.member.dto.MemberResponseDto.TeacherInfo;
import com.example.mssaembackendv2.domain.member.dto.MemberResponseDto.TokenInfo;
import com.example.mssaembackendv2.domain.worryboard.WorryBoardRepository;
import com.example.mssaembackendv2.domain.worryboard.WorryBoardService;
import com.example.mssaembackendv2.global.config.exception.BaseException;
import com.example.mssaembackendv2.global.config.exception.errorCode.MemberErrorCode;
import com.example.mssaembackendv2.global.config.security.jwt.JwtTokenProvider;
import com.example.mssaembackendv2.global.config.security.oauth.*;
import com.example.mssaembackendv2.global.s3.S3Service;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    private final SocialLoginService socialLoginService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final WorryBoardRepository worryBoardRepository;
    private final BadgeRepository badgeRepository;
    private final S3Service s3Service;
    private final BadgeService badgeService;
    private final EvaluationService evaluationService;
    private final BoardService boardService;
    private final DiscussionService discussionService;
    private final WorryBoardService worryBoardService;
    private final KaKaoSocialLoginService kaKaoSocialLoginService;
    private final GoogleSocialLoginService googleSocialLoginService;
    private final NaverSocialLoginService naverSocialLoginService;

    @Transactional
    public void save(Member member) {
        memberRepository.save(member);
    }

    @Transactional
    public Member register(RegisterMember registerMember) {
        Member member = new Member(
                registerMember.getEmail(),
                registerMember.getNickName(),
                registerMember.getMbti(),
                registerMember.getCaseSensitivity());
        save(member);
        return member;
    }

    @Transactional
    public TokenInfo registerMember(RegisterMember registerMember) {
        checkRegister(registerMember);
        Member member = register(registerMember);
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(member.getId());
        member.changeRefreshToken(tokenInfo.getRefreshToken());
        member.updateBadge(BadgeEnum.NEWBIE.getName());
        badgeService.insertBadge(new Badge(BadgeEnum.NEWBIE, member,true));
        return tokenInfo;
    }

    @Transactional
    public TokenInfo socialLogin(SocialLoginType socialLoginType,
        SocialLoginToken socialLoginToken) throws BaseException, IOException {
        String idToken = socialLoginToken.getIdToken();
        String email = "";
        switch (socialLoginType) {
            case KAKAO -> email = kaKaoSocialLoginService.kakaoSocialLogin(idToken);
            case GOOGLE -> email = googleSocialLoginService.googleSocialLogin(idToken);
            case NAVER -> email = naverSocialLoginService.naverSocialLogin(idToken);
        }

        Member member = memberRepository.findByEmail(email).orElse(null);
        if (member != null) {
            TokenInfo tokenInfo = jwtTokenProvider.generateToken(member.getId());
            member.changeRefreshToken(tokenInfo.getRefreshToken());
            return tokenInfo;
        } else {
            BaseException exception = new BaseException(MemberErrorCode.UN_REGISTERED_MEMBER);
            exception.setEmailMessage(email);
            throw exception;
        }
    }

    public void checkRegister(RegisterMember registerMember) {
        Boolean flag = memberRepository.existsByEmail(registerMember.getEmail());
        if (flag) {
            throw new BaseException(MemberErrorCode.DUPLICATE_MEMBER);
        }
        flag = memberRepository.existsByNickName(registerMember.getNickName());
        if (flag) {
            throw new BaseException(MemberErrorCode.DUPLICATE_NICKNAME);
        }
    }

    public CheckNickNameRes checkNickName(CheckNickName checkNickName) {
        return new CheckNickNameRes(
            memberRepository.existsByNickName(checkNickName.getNickName()));
    }

    // 홈 화면에 보여줄 인기 M쌤 조회
    public List<TeacherInfo> findHotTeacherForHome() {
        PageRequest pageRequest = PageRequest.of(0, 4);
        Page<Member> solveMembers = worryBoardRepository.findSolveMemberWithMoreThanOneIdAndIsSolvedTrueAndStateTrue(

            LocalDateTime.now().minusMonths(1),
            pageRequest);
        List<TeacherInfo> teacherInfos = new ArrayList<>();

        for (Member solveMember : solveMembers) {
            teacherInfos.add(
                new TeacherInfo(
                    solveMember.getId(),
                    solveMember.getNickName(),
                    solveMember.getDetailMbti(),
                    solveMember.getBadgeName(),
                    solveMember.getProfileImageUrl(),
                    solveMember.getIntroduction()
                )
            );
        }
        return teacherInfos;
    }

    @Transactional
    public String modifyProfile(Member member, ModifyProfile modifyProfile) {
        // 대표 뱃지 변경
        String badgeName = badgeService.changeRepresentativeBadge(member, modifyProfile.getBadgeId());
        // 수정
        member.modifyMember(modifyProfile.getNickName(), modifyProfile.getIntroduction(),
                modifyProfile.getMbti(), modifyProfile.getCaseSensitivity(), badgeName);
        if(modifyProfile.getChangeImageUrl() != null) {
            member.changeProfileImageUrl(modifyProfile.getChangeImageUrl());
        }
        save(member);
        return "수정 성공";
    }

    public MemberProfileInfo getProfile(Long memberId) {
        Member member = memberRepository.findByIdWithStatus(memberId)
                .orElseThrow(()-> new BaseException(MemberErrorCode.EMPTY_MEMBER));
        return MemberProfileInfo.builder()
                .teacherInfo(new TeacherInfo(member))
                .badgeInfos(badgeService.findAllBadge(member))
                .evaluationCount(evaluationService.countEvaluation(memberId))
                .boardHistory(boardService.getBoardHistory(member))
                .discussionHistory(discussionService.getDiscussionHistory(member))
                .worryBoardHistory(worryBoardService.getWorryBoardHistory(member))
                .build();
    }

    public TokenInfo refreshAccessToken(Member member) {
        String refreshToken = jwtTokenProvider.generateRefreshToken(member.getId());
        member.changeRefreshToken(refreshToken);
        save(member);
        return TokenInfo.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(member.getId()))
                .refreshToken(refreshToken)
                .build();
    }

    public MemberInfo getMemberInfo(Member member) {
        List<Badge> allByMember = badgeRepository.findAllByMember(member).orElseThrow();
        Long badgeId = null;
        for (Badge badge : allByMember) {
            if(badge.getBadgeEnum().getName().equals(member.getBadgeName())){
                badgeId = badge.getId();
                break;
            }
        }
        return new MemberInfo(member, badgeId);
    }

    public String deleteProfileImage(Member member) {
        if(!member.isDefaultProfile()) {
            member.deleteProfile();
            save(member);
            return "삭제 완료";
        } else {
            throw new BaseException(MemberErrorCode.DEFAULT_PROFILE);
        }
    }

    public String uploadFile(Member member, MultipartFile multipartFile) throws IOException {
        if (multipartFile != null) {
            // 기본 이미지가 아니라면 기존 이미지 삭제 후 새로운 이미지 업로드, 기본 이미지가 삭제 되지 않기 위함
            if(!member.isDefaultProfile()) {
                s3Service.deleteFile(s3Service.parseFileName(member.getProfileImageUrl()));
            }
            String newProfileUrl = s3Service.uploadImage(multipartFile);
            return newProfileUrl;
        }
        return null;
    }

    public String deleteFile(String imageUrl) {
        s3Service.deleteFile(s3Service.parseFileName(imageUrl));
        return "삭제 완료";
    }
}
