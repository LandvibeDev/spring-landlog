package com.landvibe.landlog.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum ErrorCode {

    // 401 Unauthorized
    INVALID_PASSWORD(UNAUTHORIZED, "잘못된 패스워드 입니다."),
    UNAUTHORIZED_USER(UNAUTHORIZED, "권한이 없는 사용자입니다."),

    // 404 Not found
    NOT_FOUND_USER(NOT_FOUND, "사용자를 찾을 수 없습니다."),
    NOT_FOUND_BLOG(NOT_FOUND, "블로그를 찾을 수 없습니다."),

    // 409 Conflict
    DUPLICATE_USER_NAME(CONFLICT, "이미 존재하는 이름입니다."),
    DUPLICATE_USER_EMAIL(CONFLICT, "이미 존재하는 이메일입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
