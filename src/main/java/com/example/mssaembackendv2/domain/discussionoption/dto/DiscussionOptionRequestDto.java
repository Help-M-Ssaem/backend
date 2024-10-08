package com.example.mssaembackendv2.domain.discussionoption.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class DiscussionOptionRequestDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetOptionReq {
        private Long id;
        private String content;
        private boolean hasImage;
    }
}