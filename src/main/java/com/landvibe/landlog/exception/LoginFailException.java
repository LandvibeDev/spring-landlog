package com.landvibe.landlog.exception;

public class LoginFailException extends RuntimeException {

    private String message;

    public LoginFailException(String message) {
        this.message = message;
    }
}
