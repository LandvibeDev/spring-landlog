package com.landvibe.landlog.exceptions;

import com.landvibe.landlog.exceptionHandler.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BlogException extends RuntimeException{
    private final ErrorCode errorCode;
}
