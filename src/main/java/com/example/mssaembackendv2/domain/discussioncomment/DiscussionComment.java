package com.example.mssaembackendv2.domain.discussioncomment;

import com.example.mssaembackendv2.domain.board.Board;
import com.example.mssaembackendv2.domain.discussion.Discussion;
import com.example.mssaembackendv2.domain.member.Member;
import com.example.mssaembackendv2.global.common.BaseTimeEntity;
import com.example.mssaembackendv2.global.common.Comment;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DiscussionComment extends BaseTimeEntity implements Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String content;

    @ColumnDefault("0")
    private Long likeCount;

    @ColumnDefault("0")
    private Integer report;

    @ColumnDefault("0")
    private Long parentId; //댓글 : 0, 대 댓글 : 자신의 부모 댓글 id

    private boolean state = true; //true : 존재, false : 삭제

    @ManyToOne(fetch = FetchType.LAZY)
    private Discussion discussion;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public DiscussionComment(String content, Member member, Discussion discussion) {
        this.content = content;
        this.member = member;
        this.discussion = discussion;
        this.discussion.increaseCommentCount();
    }

    public void increaseReport() {
        this.report++;
    }

    public void updateState() {
        this.state = false;
    }

    public void deleteComment() {
        this.content = "삭제된 댓글입니다.";
        this.likeCount = 0L;
        this.discussion.decreaseCommentCount();
        this.state = false;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    @Override
    public Board getBoard() {
        return null;
    }

    public void setParentComment(Long id) {
        this.parentId = id;
    }
}