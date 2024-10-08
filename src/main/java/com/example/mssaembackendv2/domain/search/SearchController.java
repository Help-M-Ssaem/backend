package com.example.mssaembackendv2.domain.search;

import static com.example.mssaembackendv2.global.config.exception.errorCode.SearchErrorCode.EMPTY_KEYWORD;

import com.example.mssaembackendv2.domain.member.Member;
import com.example.mssaembackendv2.domain.search.dto.SearchRequestDto.SearchInfo;
import com.example.mssaembackendv2.domain.search.dto.SearchResponseDto.SearchPopular;
import com.example.mssaembackendv2.domain.search.dto.SearchResponseDto.SearchRecent;
import com.example.mssaembackendv2.domain.search.dto.SearchResponseDto.SearchRes;
import com.example.mssaembackendv2.global.config.exception.BaseException;
import com.example.mssaembackendv2.global.config.security.auth.CurrentMember;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SearchController {

    private final SearchService searchService;

    /**
     * 전체 검색어 입력
     */
    @PostMapping("/keywords")
    public ResponseEntity<SearchRes> selectKeywords(@CurrentMember Member member,
        @RequestBody SearchInfo searchInfo) {
        if (searchInfo.getKeyword().matches("^\\s*$")) {
            throw new BaseException(EMPTY_KEYWORD);
        }
        if (member != null) {
            searchService.insertKeywords(member, searchInfo);
        }
        searchService.insertRedisSearch(searchInfo);
        return ResponseEntity.ok(searchService.selectEverySearch(searchInfo, member));
    }

    /**
     * 저장된 검색어 10개 check
     */
    @GetMapping("/redis/searchtest")
    public List<SearchPopular> selectAllSearch() {
        return searchService.selectAllSearch();
    }

    /**
     * 최근 검색어
     */
    @GetMapping("/member/recent/keywords")
    public ResponseEntity<List<SearchRecent>> selectRecentSearch(@CurrentMember Member member) {
        return ResponseEntity.ok(searchService.selectRecentSearch(member));
    }

    /**
     * 실시간 인기 검색어
     */
    @GetMapping("/realtime/keywords")
    public ResponseEntity<List<SearchPopular>> selectAllPopularSearch() {
        return ResponseEntity.ok(searchService.selectPopularSearch());
    }

}
