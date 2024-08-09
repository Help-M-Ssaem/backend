package com.example.mssaembackendv2.domain.search;

import com.example.mssaembackendv2.domain.member.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRepository extends JpaRepository<Search, Long> {

  List<Search> findAllByMemberOrderByUpdatedAtDesc(Member member);

  Search findByKeywordAndMember(String Keyword, Member member);
}
