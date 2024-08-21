package com.example.mssaembackendv2.domain.member.dto;


import com.example.mssaembackendv2.domain.badge.dto.BadgeResponse.BadgeInfo;
import com.example.mssaembackendv2.domain.board.dto.BoardResponseDto.BoardHistory;
import com.example.mssaembackendv2.domain.discussion.dto.DiscussionResponseDto.DiscussionHistory;
import com.example.mssaembackendv2.domain.evaluation.dto.EvaluationResultDto.EvaluationCount;
import com.example.mssaembackendv2.domain.member.Member;
import com.example.mssaembackendv2.domain.worryboard.dto.WorryBoardResponseDto.WorryBoardHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class MemberResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class TokenInfo {

        private String accessToken;
        private String refreshToken;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberSimpleInfo {

        private Long id;
        private String nickName;
        private String mbti;
        private String badge;
        private String profileImgUrl;
        private String introduction;

        public MemberSimpleInfo(Member member) {
            this.id = member.getId();
            this.nickName = member.getNickName();
            this.mbti = member.getDetailMbti();
            this.badge = member.getBadgeName();
            this.profileImgUrl = member.getProfileImageUrl();
            this.introduction = member.getIntroduction();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberInfo {

        private Long id;
        private String nickName;
        private String mbti;
        private String badge;
        private String profileImgUrl;
        private String introduction;
        private Long badgeId;

        public MemberInfo(Member member, Long badgeId) {
            this.id = member.getId();
            this.nickName = member.getNickName();
            this.mbti = member.getDetailMbti();
            this.badge = member.getBadgeName();
            this.profileImgUrl = member.getProfileImageUrl();
            this.introduction = member.getIntroduction();
            this.badgeId = badgeId;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class CheckNickNameRes {

        private boolean isUsed;
    }

    @Getter
    @AllArgsConstructor
    public static class TeacherInfo {

        private Long id;
        private String nickName;
        private String mbti;
        private String badge;
        private String profileImgUrl;
        private String introduction;

        public TeacherInfo(Member member) {
            this.id = member.getId();
            this.nickName = member.getNickName();
            this.mbti = member.getDetailMbti();
            this.badge = member.getBadgeName();
            this.profileImgUrl = member.getProfileImageUrl();
            this.introduction = member.getIntroduction();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MemberProfileInfo {
        private TeacherInfo teacherInfo;
        private List<BadgeInfo> badgeInfos;
        private EvaluationCount evaluationCount;
        private BoardHistory boardHistory;
        private DiscussionHistory discussionHistory;
        private WorryBoardHistory worryBoardHistory;
    }
}