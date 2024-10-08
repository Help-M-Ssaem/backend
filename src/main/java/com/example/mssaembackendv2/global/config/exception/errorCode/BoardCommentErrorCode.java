package com.example.mssaembackendv2.global.config.exception.errorCode;

import com.example.mssaembackendv2.global.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BoardCommentErrorCode implements ErrorCode {

    INVALID_MEMBER("BOARD_COMMENT_001", "게시글 댓글을 수정할 권한이 없습니다.", HttpStatus.UNAUTHORIZED),
    EMPTY_BOARD_COMMENT("BOARD_COMMENT_002","게시글 댓글이 존재하지 않습니다." , HttpStatus.NOT_FOUND);
    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
