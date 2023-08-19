package com.landvibe.landlog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MemberException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;

    public MemberException(BaseException baseException) {
        this.message = baseException.message;
        this.httpStatus = baseException.httpStatus;
    }

}
