package com.example.mssaembackendv2.domain.evaluation.dto;


import com.example.mssaembackendv2.domain.evaluation.EvaluationEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class EvaluationRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EvaluationInfo{
        private Long worryBoardId;
        private List<EvaluationEnum> evaluations;
    }
}
