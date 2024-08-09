package com.example.mssaembackendv2.global.config.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getErrorCode();

    String getMessage();

    HttpStatus getStatus();
}
