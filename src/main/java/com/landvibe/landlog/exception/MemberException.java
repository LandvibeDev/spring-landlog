package com.landvibe.landlog.exception;

import org.springframework.http.HttpStatus;

public class MemberException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public MemberException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}