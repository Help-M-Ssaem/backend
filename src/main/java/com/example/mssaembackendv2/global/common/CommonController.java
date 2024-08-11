package com.example.mssaembackendv2.global.common;

import com.example.mssaembackendv2.global.config.exception.BaseException;
import com.example.mssaembackendv2.global.config.exception.errorCode.GlobalErrorCode;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommonController {

    private final CommonService commonService;

    @GetMapping("/access/denied")
    public String accessDenied() {
        throw new BaseException(GlobalErrorCode.ACCESS_DENIED);
    }

    @GetMapping("/common/terms")
    public ResponseEntity<String> getCommunityTerms() throws IOException {
        // 서비스에서 JSON 내용을 읽어옴
        String jsonContent = commonService.getCommunityTermsJson();

        // ResponseEntity를 사용하여 JSON 응답 반환
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(jsonContent);
    }
}
