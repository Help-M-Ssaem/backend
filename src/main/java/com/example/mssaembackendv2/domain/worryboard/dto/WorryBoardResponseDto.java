package com.example.mssaembackendv2.domain.worryboard.dto;

import com.example.mssaembackendv2.domain.chatroom.ChatRoom;
import com.example.mssaembackendv2.domain.mbti.MbtiEnum;
import com.example.mssaembackendv2.domain.member.dto.MemberResponseDto.MemberSimpleInfo;
import com.example.mssaembackendv2.domain.worryboard.WorryBoard;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class WorryBoardResponseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetWorryRes {

        private MemberSimpleInfo memberSimpleInfo;
        private Long worryBoardId;
        private MbtiEnum targetMbti;
        private String title;
        private String content;
        private String createdAt;
        private List<String> imgList;
        private Boolean isEditAllowed;
        private Boolean isChatAllowed;
        private Boolean isSolved;
        private Long hits;

        @Builder
        public GetWorryRes(WorryBoard worryBoard, List<String> imgList,
            MemberSimpleInfo memberSimpleInfo, String createdAt, Boolean isEditAllowed,
            Boolean isChatAllowed) {
            this.worryBoardId = worryBoard.getId();
            this.memberSimpleInfo = memberSimpleInfo;
            this.targetMbti = worryBoard.getTargetMbti();
            this.title = worryBoard.getTitle();
            this.content = worryBoard.getContent();
            this.createdAt = createdAt;
            this.imgList = imgList;
            this.isEditAllowed = isEditAllowed;
            this.isChatAllowed = isChatAllowed;
            this.isSolved = worryBoard.getIsSolved();
            this.hits = worryBoard.getHits();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetWorriesRes {

        private Long id;
        private String title;
        private String content;
        private MbtiEnum memberMbti;
        private MbtiEnum targetMbti;
        private String createdDate;
        private String imgUrl;

        @Builder
        public GetWorriesRes(WorryBoard worryBoard, String imgUrl, String createdAt) {
            this.id = worryBoard.getId();
            this.title = worryBoard.getTitle();
            this.content = worryBoard.getContent();
            this.memberMbti = worryBoard.getMember().getMbti();
            this.targetMbti = worryBoard.getTargetMbti();
            this.createdDate = createdAt;
            this.imgUrl = imgUrl;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class PatchWorrySolvedRes {

        private MemberSimpleInfo memberSimpleInfo;
        private Long worryBoardId;
        private Long writerId;

        @Builder
        public PatchWorrySolvedRes(MemberSimpleInfo memberSimpleInfo, Long worryBoardId, Long writerId) {
            this.memberSimpleInfo = memberSimpleInfo;
            this.worryBoardId = worryBoardId;
            this.writerId = writerId;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WorryBoardHistory {

        private int worryBoardCount;         // 내가 작성한 고민글의 수
        private int solvedWorryBoardCount;   // 내가 해결한 고민글의 수
        private int evaluationCount;         // 내가 남긴 평가의 수
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetWorriesSearchRes {

        private Long id;
        private String title;
        private String content;
        private MbtiEnum memberMbti;
        private MbtiEnum targetMbti;
        private String createdDate;
        private boolean isSolved;
        private String thumbnail;

        @Builder
        public GetWorriesSearchRes(WorryBoard worryBoard, String thumbnail, String createdAt) {
            this.id = worryBoard.getId();
            this.title = worryBoard.getTitle();
            this.content = worryBoard.getContent();
            this.memberMbti = worryBoard.getMember().getMbti();
            this.targetMbti = worryBoard.getTargetMbti();
            this.createdDate = createdAt;
            this.isSolved = worryBoard.getIsSolved();
            this.thumbnail = thumbnail;
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class GetWorryBoardId {

        private Long worryBoardId;
    }

}

