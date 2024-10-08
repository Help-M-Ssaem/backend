package com.example.mssaembackendv2.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

public class SearchRequestDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SearchReq {

    private int type; // 제목+내용, 제목, 내용, 글쓴이로 순서대로 0,1,2,3을 의미
    private String keyword; // 검색어
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SearchInfo implements Serializable {

    private String keyword;
  }


}
