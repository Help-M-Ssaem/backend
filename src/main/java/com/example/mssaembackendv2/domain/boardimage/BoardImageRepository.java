package com.example.mssaembackendv2.domain.boardimage;

import com.example.mssaembackendv2.domain.board.Board;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {

    @Query("SELECT bi.imageUrl FROM BoardImage bi WHERE bi.board = :board ORDER BY bi.id LIMIT 1")
    Optional<String> findImageUrlByBoardOrderById(@Param("board") Board board);

    List<BoardImage> findAllByBoardId(Long id);

    List<BoardImage> deleteAllByBoard(Board board);

    void deleteBoardImageByImageUrl(String imageUrl);
}
