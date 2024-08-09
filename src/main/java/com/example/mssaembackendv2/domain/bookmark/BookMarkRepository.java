package com.example.mssaembackendv2.domain.bookmark;

import com.example.mssaembackendv2.domain.mbti.MbtiEnum;
import com.example.mssaembackendv2.domain.member.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    BookMark findByMemberAndMbti(Member member, MbtiEnum mbtiEnum);

    Boolean existsBookMarkByMemberAndMbti(Member member, MbtiEnum mbtiEnum);

    List<BookMark> findAllByStateIsTrueAndMember(Member member);
}
