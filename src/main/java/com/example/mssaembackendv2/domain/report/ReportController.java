package com.example.mssaembackendv2.domain.report;

import com.example.mssaembackendv2.domain.member.Member;
import com.example.mssaembackendv2.domain.report.dto.ReportRequestDto.ReportReq;
import com.example.mssaembackendv2.global.config.security.auth.CurrentMember;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/member/reports")
    public ResponseEntity<String> report(@CurrentMember Member member,@RequestBody ReportReq reportReq)
        throws MessagingException {
        return ResponseEntity.ok(reportService.report(member, reportReq));
    }

}
