package com.landvibe.landlog;

public enum ErrorMessage {
    WRONG_PASSWORD("WRONG_PASSWORD"),
    WRONG_EMAIL("WRONG_EMAIL"),
    EXIST_MEMBER("EXIST_MEMBER");

    public String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
