package com.landvibe.landlog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.landvibe.landlog.ErrorMessage.*;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BlogException.class)
    public ResponseEntity<String> blogExceptionHandler(BlogException e) {
        if (e.getMessage().equals(EMPTY_BLOG_ID.message)) {
            return new ResponseEntity<>("failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        if (e.getMessage().equals(NO_MATCH_BLOG_WITH_BLOG_ID.message)) {
            return new ResponseEntity<>("failed: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<String> memberExceptionHandler(MemberException e) {
        if (e.getMessage().equals(EMPTY_CREATOR_ID.message)) {
            return new ResponseEntity<>("failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        if (e.getMessage().equals(NO_MATCH_MEMBER_WITH_CREATOR_ID.message)) {
            return new ResponseEntity<>("failed: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
        if (e.getMessage().equals(EXIST_MEMBER.message)) {
            return new ResponseEntity<>("failed: " + e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<String> loginExceptionHandler(LoginException e) {
        if (e.getMessage().equals(WRONG_EMAIL.message)) {
            return new ResponseEntity<>("failed: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
        if (e.getMessage().equals(WRONG_PASSWORD.message)) {
            return new ResponseEntity<>("failed: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}