package com.example.mssaembackendv2.domain.evaluation.dto;

import com.example.mssaembackendv2.domain.evaluation.EvaluationEnum;
import com.example.mssaembackendv2.domain.member.Member;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class EvaluationResultDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class EvaluationResult{
    private Long worryBoardId;
    private List<EvaluationEnum> evaluations;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class EvaluationCount{
    private int likeCount;
    private int usefulCount;
    private int funCount;
    private int sincereCount;
    private int hotCount;

    public EvaluationCount(int[] result) {
      this.likeCount = result[0];
      this.usefulCount = result[1];
      this.funCount = result[2];
      this.sincereCount = result[3];
      this.hotCount = result[4];
    }
  }

}
