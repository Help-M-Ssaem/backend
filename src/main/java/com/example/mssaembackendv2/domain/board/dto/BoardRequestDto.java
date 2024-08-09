package com.example.mssaembackendv2.domain.board.dto;

import com.example.mssaembackendv2.domain.mbti.MbtiEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BoardRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostBoardReq {

        private String title;
        private String content;
        private MbtiEnum mbti;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PatchBoardReq {

        private String title;
        private String content;
        private MbtiEnum mbti;
    }
}