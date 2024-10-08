package com.example.mssaembackendv2.domain.discussionoption;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class DiscussionOptionController {

    private final DiscussionOptionService discussionOptionService;

    @PostMapping("/member/discussion-options/files")
    public ResponseEntity<String> uploadFiles(
        @RequestPart(value="image", required = false)MultipartFile multipartFile)
        throws IOException {
        return ResponseEntity.ok(discussionOptionService.uploadFile(multipartFile));
    }
}
