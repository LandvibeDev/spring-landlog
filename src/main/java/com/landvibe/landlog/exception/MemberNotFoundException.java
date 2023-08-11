package com.landvibe.landlog.exception;

public class MemberNotFoundException extends RuntimeException {

    private String message;

    public MemberNotFoundException(String message) {
        this.message = message;
    }
}
