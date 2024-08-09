package com.example.mssaembackendv2.global.common;

import com.example.mssaembackendv2.domain.board.Board;
import com.example.mssaembackendv2.domain.discussion.Discussion;
import com.example.mssaembackendv2.domain.member.Member;

import java.time.LocalDateTime;

public interface Comment {
    Long getId();
    Long getParentId();
    String getContent();
    Long getLikeCount();
    LocalDateTime getCreatedAt();
    Member getMember();
    Discussion getDiscussion();
    Board getBoard();

    void setParentComment(Long commentId);

    void deleteComment();
}