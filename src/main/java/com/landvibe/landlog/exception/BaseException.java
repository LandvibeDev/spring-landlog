package com.landvibe.landlog.exception;

import org.springframework.http.HttpStatus;

public enum BaseException {
    WRONG_PASSWORD("비밀번호가 틀렸습니다", HttpStatus.BAD_REQUEST),
    NO_USER("해당 정보와 일치하는 회원이 존재하지 않습니다", HttpStatus.BAD_REQUEST),
    NO_BLOG("해당 정보와 일치하는 게시글이 존재하지 않습니다", HttpStatus.BAD_REQUEST),
    ALREADY_EXIST("이미 존재하는 회원입니다.", HttpStatus.BAD_REQUEST);

    public String message;
    public HttpStatus httpStatus;

    BaseException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
