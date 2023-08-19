package com.landvibe.landlog.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(BlogException.class)
    public ResponseEntity<String> blogExceptionHandler(BlogException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<String> MemberExceptionHandler(MemberException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }
}
