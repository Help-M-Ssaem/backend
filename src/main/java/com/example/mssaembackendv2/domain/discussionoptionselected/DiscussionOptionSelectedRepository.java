package com.example.mssaembackendv2.domain.discussionoptionselected;

import com.example.mssaembackendv2.domain.discussion.Discussion;
import com.example.mssaembackendv2.domain.discussionoption.DiscussionOption;
import com.example.mssaembackendv2.domain.member.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiscussionOptionSelectedRepository extends
    JpaRepository<DiscussionOptionSelected, Long> {

    DiscussionOptionSelected findDiscussionOptionSelectedByMemberAndDiscussionOptionAndStateTrue(
        Member member, DiscussionOption beforeDiscussionOption);

    @Query("SELECT dos FROM DiscussionOptionSelected dos "
        + "JOIN dos.discussionOption option "
        + "WHERE dos.member = :member "
        + "AND option.discussion = :discussion ORDER BY option.id ASC")
    List<DiscussionOptionSelected> findAllByMemberAndDiscussionOrderByOptionIdAsc(@Param("member") Member member, @Param("discussion") Discussion discussion);
}