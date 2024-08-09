package com.example.mssaembackendv2.domain.bookmark.dto;

import com.example.mssaembackendv2.domain.mbti.MbtiEnum;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BookMarkResponseDto {

    @Getter
    @NoArgsConstructor
    public static class BookMarkInfo {

        private List<MbtiEnum> mbti;
        public BookMarkInfo (List<MbtiEnum> mbti){
            this.mbti = mbti;
        }
    }
}
