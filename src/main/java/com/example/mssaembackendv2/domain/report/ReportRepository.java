package com.example.mssaembackendv2.domain.report;

import com.example.mssaembackendv2.domain.member.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Report findTopByResourceIdAndReportTargetAndMemberOrderByIdDesc(Long resourceId,
        ReportTarget reportTarget, Member member);

    List<Report> findByResourceIdAndReportTarget(Long resourceId, ReportTarget reportTarget);
}
