package com.landvibe.landlog.exception;

import org.springframework.http.HttpStatus;

public class BlogException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public BlogException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}