package com.example.mssaembackendv2.domain.discussioncommentlike;

import com.example.mssaembackendv2.domain.discussioncomment.DiscussionComment;
import com.example.mssaembackendv2.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiscussionCommentLikeRepository extends
    JpaRepository<DiscussionCommentLike, Long> {

    Boolean existsDiscussionCommentLikeByMemberAndStateIsTrueAndDiscussionCommentId(Member member, Long id);

    @Query(value = "SELECT dc FROM DiscussionComment dc WHERE dc.discussion.id = :discussionId AND dc.likeCount >= 10 AND dc.state = true ORDER BY dc.likeCount DESC")
    Page<DiscussionComment> findDiscussionCommentsByDiscussionIdWithMoreThanTenDiscussionCommentLikeAndStateTrue(
        PageRequest pageRequest, @Param("discussionId") Long discussionId);

    DiscussionCommentLike findDiscussionCommentLikeByMemberAndDiscussionCommentId(Member member, Long commentId);

    void deleteAllByDiscussionComment(DiscussionComment discussionComment);
}
