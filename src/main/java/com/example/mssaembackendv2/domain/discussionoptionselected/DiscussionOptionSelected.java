package com.example.mssaembackendv2.domain.discussionoptionselected;

import com.example.mssaembackendv2.domain.discussionoption.DiscussionOption;
import com.example.mssaembackendv2.domain.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DiscussionOptionSelected {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean state = true; // true : 선택함 , false : 선택 안함

    @ManyToOne(fetch = FetchType.LAZY)
    private DiscussionOption discussionOption;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    DiscussionOptionSelected(DiscussionOption discussionOption, Member member) {
        this.discussionOption = discussionOption;
        this.member = member;
    }

    public void changeSelected() {
        this.state = true;
    }

    public void changeUnselected() {
        this.state = false;
    }
}
