package com.example.mssaembackendv2.domain.board;

import com.example.mssaembackendv2.domain.mbti.MbtiEnum;
import com.example.mssaembackendv2.domain.member.Member;
import com.example.mssaembackendv2.global.common.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;


@DynamicInsert
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ColumnDefault("0")
    private Long likeCount;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MbtiEnum mbti;

    @ColumnDefault("0")
    private Integer report;

    @ColumnDefault("0")
    private Long hits;

    private boolean state = true; //true : 삭제아님, false : 삭제

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String thumbnail;
    @ColumnDefault("0")
    private Long commentCount;

    @Builder
    public Board(String title, String content, MbtiEnum mbti, Member member, String thumbnail) {
        this.title = title;
        this.content = content;
        this.mbti = mbti;
        this.member = member;
        this.thumbnail = thumbnail;
    }

    public void modifyBoard(String title, String content, MbtiEnum mbti) {
        this.title = title != null ? title : this.title;
        this.content = content != null ? content : this.content;
        this.mbti = mbti != null ? mbti : this.mbti;
    }

    public void deleteBoard() {
        this.state = false;
    }

    public void changeThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public void increaseReport() {
        this.report++;
    }

    public void updateState() {
        this.state = false;
    }

    public void increaseCommentCount() {
        this.commentCount++;
    }

    public void decreaseCommentCount() {
        this.commentCount--;
    }

    public void increaseHits() {
        this.hits++;
    }
}
