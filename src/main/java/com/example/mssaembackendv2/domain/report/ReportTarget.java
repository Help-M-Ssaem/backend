package com.example.mssaembackendv2.domain.report;

import lombok.Getter;

public enum ReportTarget {
    BOARD("게시물"),
    DISCUSSION("토론글"),
    WORRY("고민글"),
    MEMBER("유저"),
    BOARD_COMMENT("게시물 댓글"),
    DISCUSSION_COMMENT("토론글 댓글");

    @Getter
    private final String name;

    ReportTarget(String name) {
        this.name = name;
    }
}
