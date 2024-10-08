package com.example.mssaembackendv2.global.config.exception.errorCode;

import com.example.mssaembackendv2.global.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum WorryBoardErrorCode implements ErrorCode {
  EMPTY_WORRY_BOARD("WORRY-BOARD_001", "존재하지 않는 게시물입니다.", HttpStatus.NOT_FOUND);


  private final String errorCode;
  private final String message;
  private final HttpStatus status;
}
