package com.example.mssaembackendv2.domain.evaluation;

import com.example.mssaembackendv2.domain.member.Member;
import com.example.mssaembackendv2.domain.worryboard.WorryBoard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

  Evaluation findByWorryBoardAndMember(WorryBoard worryBoard, Member member);

  List<Evaluation> findAllByMember(Member member);

  Integer countAllByMember(Member member);
}
