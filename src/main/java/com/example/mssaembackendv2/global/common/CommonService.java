package com.example.mssaembackendv2.global.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonService {

    public String getCommunityTermsJson() throws IOException {
        // ClassPathResource를 이용해 JSON 파일 읽어오기
        ClassPathResource resource = new ClassPathResource("community-terms.json");

        // 파일 내용을 String으로 변환
        Path path = resource.getFile().toPath();
        return new String(Files.readAllBytes(path));
    }
}
