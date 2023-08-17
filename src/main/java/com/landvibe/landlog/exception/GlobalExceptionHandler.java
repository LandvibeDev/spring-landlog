package com.landvibe.landlog.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BlogException.class)
    public ResponseEntity<String> blogExceptionHandler(BlogException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<String> memberExceptionHandler(MemberException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<String> loginExceptionHandler(LoginException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

}