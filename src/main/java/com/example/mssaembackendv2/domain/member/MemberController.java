package com.example.mssaembackendv2.domain.member;

import com.example.mssaembackendv2.domain.member.dto.MemberRequestDto.CheckNickName;
import com.example.mssaembackendv2.domain.member.dto.MemberRequestDto.ModifyProfile;
import com.example.mssaembackendv2.domain.member.dto.MemberRequestDto.RegisterMember;
import com.example.mssaembackendv2.domain.member.dto.MemberRequestDto.SocialLoginToken;
import com.example.mssaembackendv2.domain.member.dto.MemberResponseDto;
import com.example.mssaembackendv2.domain.member.dto.MemberResponseDto.CheckNickNameRes;
import com.example.mssaembackendv2.domain.member.dto.MemberResponseDto.MemberProfileInfo;
import com.example.mssaembackendv2.domain.member.dto.MemberResponseDto.MemberInfo;
import com.example.mssaembackendv2.domain.member.dto.MemberResponseDto.TeacherInfo;
import com.example.mssaembackendv2.domain.member.dto.MemberResponseDto.TokenInfo;
import com.example.mssaembackendv2.global.config.security.auth.CurrentMember;
import com.example.mssaembackendv2.global.config.security.oauth.SocialLoginType;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    /**
     * [POST] 소셜 회원가입
     */
    @PostMapping("/sign-up")
    public ResponseEntity<TokenInfo> register(
            @Valid @RequestBody RegisterMember registerMember) {
        return new ResponseEntity<>
                (memberService.registerMember(registerMember), HttpStatus.OK);
    }

    /**
     * [POST] 소셜 로그인
     */
    @PostMapping("/{socialLoginType}/login")
    public ResponseEntity<TokenInfo> socialLogin(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
            @Valid @RequestBody SocialLoginToken socialLoginToken) throws IOException {
        return new ResponseEntity<>(
                memberService.socialLogin(socialLoginType, socialLoginToken), HttpStatus.OK);
    }

    /**
     * [POST] 닉네임 중복 확인
     */
    @PostMapping("/nick-name")
    public ResponseEntity<CheckNickNameRes> checkNickName(
            @Valid @RequestBody CheckNickName checkNickName) {
        return new ResponseEntity<>(memberService.checkNickName(checkNickName),
                HttpStatus.OK);
    }

    /**
     * 홈 화면 조회 - 인기 M쌤
     */
    @GetMapping("/teacher")
    public ResponseEntity<List<TeacherInfo>> findHotTeacherForHome() {
        return ResponseEntity.ok(memberService.findHotTeacherForHome());
    }

    /**
     * [PATCH] 프로필 수정
     */
    @PatchMapping("/member/profile")
    public ResponseEntity<String> modifyProfile(
            @CurrentMember Member member, @RequestBody ModifyProfile modifyProfile) {
        return new ResponseEntity<>(memberService.modifyProfile(member, modifyProfile), HttpStatus.OK);
    }

    /**
     * [GET] 프로필 조회
     */
    @GetMapping("/profile/{id}")
    public ResponseEntity<MemberProfileInfo> getProfile(
            @PathVariable("id") Long memberId) {
        return new ResponseEntity<>(memberService.getProfile(memberId), HttpStatus.OK);
    }

    /**
     * [PATCH] 로그인 토큰 갱신
     */
    @PatchMapping("/member/refresh")
    public ResponseEntity<TokenInfo> refreshLogin(@CurrentMember Member member) {
        return new ResponseEntity<>(memberService.refreshAccessToken(member), HttpStatus.OK);
    }

    /**
     * [GET] 로그인한 유저 정보 조회
     */
    @GetMapping("/member/info")
    public ResponseEntity<MemberInfo> getCurrentMemberInfo(@CurrentMember Member member) {
        return new ResponseEntity<>(memberService.getMemberInfo(member), HttpStatus.OK);
    }

    /**
     * [POST] 프로필 이미지 등록
     */
    @PostMapping("/member/profile/file")
    public ResponseEntity<String> uploadFiles(@CurrentMember Member member,
            @RequestPart(value = "image", required = false) MultipartFile multipartFile)
        throws IOException {
        return new ResponseEntity<>(memberService.uploadFile(member, multipartFile), HttpStatus.OK);
    }

    /**
     * [DELETE] 프로필 이미지 삭제
     */
    @DeleteMapping("/member/profile")
    public ResponseEntity<String> deleteProfileImage(@CurrentMember Member member) {
        return new ResponseEntity<>(memberService.deleteProfileImage(member), HttpStatus.OK);
    }

    @PostMapping("/member/s3/file")
    public ResponseEntity<String> deleteS3File(@RequestParam String imageUrl) {
        return new ResponseEntity<>(memberService.deleteFile(imageUrl), HttpStatus.OK);
    }

}
