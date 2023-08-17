package com.landvibe.landlog.exception;

import org.springframework.http.HttpStatus;

public class LoginException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public LoginException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}