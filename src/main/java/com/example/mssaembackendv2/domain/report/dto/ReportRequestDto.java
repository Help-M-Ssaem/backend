package com.example.mssaembackendv2.domain.report.dto;

import com.example.mssaembackendv2.domain.report.ReportReason;
import com.example.mssaembackendv2.domain.report.ReportTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReportRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportReq {

        Long resourceId; // 신고 대상 id
        ReportTarget reportTarget; // 신고 대상 타입
        ReportReason reportReason; // 신고 사유
        String content; // 기타 내용
    }
}
