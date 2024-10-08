package com.example.mssaembackendv2.global.config.exception.errorCode;

import com.example.mssaembackendv2.global.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BoardErrorCode implements ErrorCode {

    INVALID_MEMBER("BOARD_001", "게시글을 수정할 권한이 없습니다.", HttpStatus.UNAUTHORIZED),
    EMPTY_BOARD("BOARD_002"," 게시글이 존재하지 않습니다." , HttpStatus.NOT_FOUND);
    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
