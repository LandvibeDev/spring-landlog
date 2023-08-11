package com.landvibe.landlog.exception;

public class BlogNotFoundException extends RuntimeException {

    private String message;

    public BlogNotFoundException(String message) {
        this.message = message;
    }
}
