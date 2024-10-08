package com.example.mssaembackendv2.domain.boardcomment;

import com.example.mssaembackendv2.domain.board.Board;
import com.example.mssaembackendv2.domain.member.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {

    Long countByBoardAndStateTrue(Board board);

    List<BoardComment> findAllByBoardId(Long id);

    Long countAllByStateIsTrueAndMember(@Param("member") Member member);

    @Query(value = "SELECT bc FROM BoardComment bc JOIN FETCH bc.member WHERE bc.board.id = :id ORDER BY bc.createdAt ASC", countQuery = "SELECT count(bc) FROM BoardComment bc")
    Page<BoardComment> findAllByBoardId(@Param("id") Long id, Pageable pageable);

    Optional<BoardComment> findByIdAndStateIsTrue(Long id);

    BoardComment findByIdAndBoardIdAndStateIsTrue(Long id, Long boardId);

    Page<BoardComment> findAllByMemberIdAndStateIsTrue(Long memberId, Pageable pageable);

    void deleteAllByBoardId(Long postId);
}