package com.example.mssaembackendv2.domain.search;

import com.example.mssaembackendv2.domain.search.dto.SearchRequestDto.SearchInfo;
import com.example.mssaembackendv2.domain.search.dto.SearchResponseDto.SearchPopular;
import jakarta.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SearchCustomRepository implements Serializable {

  private static final String KEYWORD = "KEYWORD";
  @Resource(name = "redisTemplate")
  private ZSetOperations<String, String> zSetOperations;

  /**
   * Redis에 검색어 저장
   */
  public void insertRedis(SearchInfo searchInfo) {
    Double count = zSetOperations.score(KEYWORD, searchInfo.getKeyword());
    if (count == null) {
      zSetOperations.add(KEYWORD, searchInfo.getKeyword(), 1.0);
    } else {
      zSetOperations.incrementScore(KEYWORD, searchInfo.getKeyword(), 1.0);
    }
  }

  /**
   * Redis에 검색어 잘 저장했는지 test
   */
  public List<SearchPopular> selectAllSearch() {
    Set<String> range = zSetOperations.range(KEYWORD, 0, 10);
    List<SearchPopular> result = new ArrayList<>();
    for (String s : range) {
      result.add(new SearchPopular(s, zSetOperations.score(KEYWORD, s)));
    }
    return result;
  }

  /**
   * 실시간 인기 검색어 출력
   */
  public List<SearchPopular> selectAllPopular(){
    return zSetOperations.reverseRange(KEYWORD, 0, 9).stream()
        .map(s -> new SearchPopular(s,zSetOperations.score(KEYWORD,s)))
        .collect(Collectors.toList());
  }
}
