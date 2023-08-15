package com.landvibe.landlog.exception;

public class MemberException extends RuntimeException {
    private String message;

    public MemberException(String message) {
        super(message);
    }
}