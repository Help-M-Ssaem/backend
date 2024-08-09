package com.example.mssaembackendv2.domain.boardcomment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BoardCommentRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostBoardCommentReq {

        private String content;
    }
}
