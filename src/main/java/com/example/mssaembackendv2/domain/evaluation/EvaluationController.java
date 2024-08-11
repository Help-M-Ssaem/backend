package com.example.mssaembackendv2.domain.evaluation;

import com.example.mssaembackendv2.domain.evaluation.dto.EvaluationRequestDto.EvaluationInfo;
import com.example.mssaembackendv2.domain.evaluation.dto.EvaluationResultDto.EvaluationCount;
import com.example.mssaembackendv2.domain.evaluation.dto.EvaluationResultDto.EvaluationResult;
import com.example.mssaembackendv2.domain.member.Member;
import com.example.mssaembackendv2.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class EvaluationController {

  private final EvaluationService evaluationService;

  /**
   * 평가 추가
   */
  @PostMapping("/member/evaluations")
  public ResponseEntity<String> insertEvaluation(@CurrentMember Member member, @RequestBody EvaluationInfo evaluationInfo) {
    return ResponseEntity.ok(evaluationService.insertEvaluation(member, evaluationInfo));
  }

  /**
   * 평가 받은 사람의 평가 내용 조회
   */
  @GetMapping("/member/evaluations/{worryBoardId}")
  public ResponseEntity<EvaluationResult> selectEvaluation(@CurrentMember Member member, @PathVariable Long worryBoardId){
    return ResponseEntity.ok(evaluationService.selectEvaluation(member, worryBoardId));
  }

  /**
   * 자신이 받은 평가 count
   */
  @GetMapping("/evaluations/count")
  public ResponseEntity<EvaluationCount> countEvaluation(@RequestParam Long memberId){
    return ResponseEntity.ok(evaluationService.countEvaluation(memberId));
  }

}
