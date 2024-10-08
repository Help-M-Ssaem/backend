package com.example.mssaembackendv2.domain.badge;

import java.util.ArrayList;
import java.util.List;

import com.example.mssaembackendv2.domain.badge.dto.BadgeResponse.*;
import com.example.mssaembackendv2.domain.member.Member;
import com.example.mssaembackendv2.global.config.exception.BaseException;
import com.example.mssaembackendv2.global.config.exception.errorCode.BadgeErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BadgeService {

    private final BadgeRepository badgeRepository;

    //멤버의 대표 뱃지 가져오기
    public String findRepresentativeBadgeByMember(Member member) {
        return badgeRepository.findNameMemberAndStateTrue(member).orElse(null).getName();
    }

    public List<BadgeInfo> findAllBadge(Member member) {
        List<Badge> badges = badgeRepository.findAllByMember(member).orElse(null);
        List<BadgeInfo> result = new ArrayList<>();
        if (badges != null) {
            badges.forEach(badge -> result.add(
                new BadgeInfo(badge.getId(), badge.getBadgeEnum().getName(), badge.isState(),
                    badge.getBadgeEnum().getUrl())));
        }
        return result;
    }

    /**
     * member와 바꾸려는 대표 뱃지로 바꾸려는 badgeId를 받아서 유효성 검사 후 바꿔주는 함수
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public String changeRepresentativeBadge(Member member, Long badgeId) {
        if (badgeId != null) {
            // 새로운 뱃지 받아오기, 멤버가 획득한 뱃지가 맞는지 유효성 검사
            Badge newBadge = badgeRepository.findByIdAndMember(badgeId, member)
                .orElseThrow(() -> new BaseException(BadgeErrorCode.EMPTY_BADGE));
            // 예전 뱃지 받아오기
            Badge oldBadge = badgeRepository.findByMemberAndStateTrue(member).orElse(null);
            // 예전 뱃지가 존재한다면 false 로 설정
            if (oldBadge != null) {
                oldBadge.changeStateFalse();
            }
            // 새로운 뱃지를 true 로 변경
            newBadge.changeStateTrue();
            return newBadge.getBadgeEnum().getName();
        }
        return member.getBadgeName();
    }

    public void insertBadge(Badge badge) {
        badgeRepository.save(badge);
    }

    public boolean existBadgeStateTrue(Member member) {
        return badgeRepository.existsByMemberAndStateIsTrue(member);
    }
}
