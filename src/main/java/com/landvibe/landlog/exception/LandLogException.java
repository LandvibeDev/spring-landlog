package com.landvibe.landlog.exception;

public class LandLogException extends RuntimeException {
    private final ErrorCode errorCode;

    public LandLogException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
