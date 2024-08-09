package com.example.mssaembackendv2.domain.discussioncomment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class DiscussionCommentRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostDiscussionCommentReq {
        private String content;
    }
}
