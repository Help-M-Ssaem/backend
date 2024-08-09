package com.example.mssaembackendv2.domain.notification;

import com.example.mssaembackendv2.domain.member.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findByMemberOrderByCreatedAtDesc(Member member, PageRequest pageRequest);

    List<Notification> findByMember(Member member);

    List<Notification> findByMemberAndStateIsFalse(Member member);

    Optional<Notification> findByIdAndStateIsTrue(Long id);

}
