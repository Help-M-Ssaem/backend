package com.example.mssaembackendv2.domain.worryboardimage;

import com.example.mssaembackendv2.domain.worryboard.WorryBoard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorryBoardImageRepository extends JpaRepository<WorryBoardImage, Long> {

    List<WorryBoardImage> findAllByWorryBoard(WorryBoard worryBoard);

    WorryBoardImage findTopByWorryBoardOrderById(WorryBoard worryBoard);

    void deleteAllByWorryBoard(WorryBoard worryBoard);

    void deleteWorryBoardImageByImgUrl(String imgUrl);
}