package com.example.mssaembackendv2.domain.badge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BadgeResponse {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BadgeInfo {
        private Long id;
        private String name;
        private boolean status;
        private String imgUrl;
    }
}
