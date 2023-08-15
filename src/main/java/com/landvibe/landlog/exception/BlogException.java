package com.landvibe.landlog.exception;

public class BlogException extends RuntimeException {
    private String message;

    public BlogException(String message) {
        super(message);
    }

}