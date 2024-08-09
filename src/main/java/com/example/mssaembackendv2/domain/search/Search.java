package com.example.mssaembackendv2.domain.search;

import com.example.mssaembackendv2.domain.member.Member;
import com.example.mssaembackendv2.global.common.BaseTimeEntity;
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

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Search extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String keyword;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  public Search(String keyword, Member member) {
    this.keyword = keyword;
    this.member = member;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }
}
