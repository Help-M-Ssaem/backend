package com.example.mssaembackendv2.domain.worryboard;

import com.example.mssaembackendv2.domain.mbti.MbtiEnum;
import com.example.mssaembackendv2.domain.member.Member;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorryBoardRepository extends JpaRepository<WorryBoard, Long> {

    @Query("SELECT wb FROM WorryBoard wb WHERE wb.state = true AND wb.isSolved = :isSolved AND ( :worryBoardId IS NULL OR wb.id <> :worryBoardId) order by wb.createdAt desc")
    Page<WorryBoard> findByIsSolvedAndStateTrueOrderByCreatedAtDesc(
        @Param("isSolved") boolean isSolved, @Param("worryBoardId") Long worryBoardId,
        Pageable pageable);

    Page<WorryBoard> findByMemberIdAndStateTrueOrderByCreatedAtDesc(Long memberId,
        Pageable pageable);

    Page<WorryBoard> findBySolveMemberIdAndStateTrueOrderByCreatedAtDesc(Long memberId,
        Pageable pageable);

    List<WorryBoard> findTop7ByIsSolvedFalseAndStateTrueOrderByCreatedAtDesc();

    WorryBoard findTopByStateTrueAndIsSolvedFalseOrderByCreatedAtDesc();

    @Query("SELECT wb FROM WorryBoard wb WHERE wb.state = true AND wb.isSolved = :isSolved AND (:fromMbti IS NULL OR wb.member.mbti = :fromMbti) AND (:toMbti IS NULL OR wb.targetMbti = :toMbti) ORDER BY wb.createdAt DESC")
    Page<WorryBoard> findWorriesBySolvedAndBothMbtiAndStateTrue(
        @Param("isSolved") Boolean isSolved,
        @Param("fromMbti") MbtiEnum fromMbti,
        @Param("toMbti") MbtiEnum toMbti,
        Pageable pageable
    );

    @Query("SELECT wb.solveMember FROM WorryBoard wb WHERE wb.solvedAt >= :oneMonthAgo AND wb.state = true GROUP BY wb.solveMember.id HAVING COUNT(wb.solveMember.id) >= 1 ORDER BY COUNT(wb.solveMember.id) DESC, wb.solveMember.id")
    Page<Member> findSolveMemberWithMoreThanOneIdAndIsSolvedTrueAndStateTrue(
        @Param("oneMonthAgo") LocalDateTime oneMonthAgo, PageRequest pageRequest);

    // 고민글 검색하기
    @Query(value = "SELECT wb FROM WorryBoard wb WHERE"
        + "(    (:searchType = 0 AND (LOWER(wb.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(wb.content) LIKE LOWER(CONCAT('%', :keyword, '%'))))"
        + " OR (:searchType = 1 AND LOWER(wb.title) LIKE LOWER(CONCAT('%', :keyword, '%')))"
        + " OR (:searchType = 2 AND LOWER(wb.content) LIKE LOWER(CONCAT('%', :keyword, '%')))"
        + " OR (:searchType = 3 AND LOWER(wb.member.nickName) LIKE LOWER(CONCAT('%', :keyword, '%'))) )"
        + " AND wb.isSolved = :isSolved AND (:fromMbti IS NULL OR wb.member.mbti = :fromMbti) AND (:toMbti IS NULL OR wb.targetMbti = :toMbti)ORDER BY wb.createdAt DESC",
        countQuery = "SELECT COUNT(w) FROM WorryBoard w")
    Page<WorryBoard> searchWorriesBySolvedAndTypeAndMbti(
        @Param("searchType") int searchType,
        @Param("keyword") String keyword,
        @Param("isSolved") Boolean isSolved,
        @Param("fromMbti") MbtiEnum fromMbti,
        @Param("toMbti") MbtiEnum toMbti,
        Pageable pageable);


    @Query(value = "select w from WorryBoard w join fetch w.member "
        + "where( (lower(w.title) like lower(concat('%', :keyword, '%')))"
        + "or (lower(w.content) like lower(concat('%', :keyword, '%')))"
        + "or (lower(w.member.nickName) like lower(concat('%', :keyword, '%'))) )"
        + "and w.state = true order by w.createdAt desc",
        countQuery = "select count(w) from WorryBoard w")
    Page<WorryBoard> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    Optional<WorryBoard> findByIdAndStateIsTrue(Long id);

    Integer countAllByStateIsTrueAndMember(@Param("member") Member member);

    Integer countALlBySolveMember(@Param("solveMember") Member solveMember);

}