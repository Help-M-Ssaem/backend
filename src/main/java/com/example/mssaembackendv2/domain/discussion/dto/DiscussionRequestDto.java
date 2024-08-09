package com.example.mssaembackendv2.domain.discussion.dto;

import com.example.mssaembackendv2.domain.discussionoption.dto.DiscussionOptionRequestDto.GetOptionReq;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class DiscussionRequestDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DiscussionReq {
        String title;
        String content;
        List<GetOptionReq> getOptionReqs;
    }
    
}