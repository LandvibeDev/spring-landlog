package com.landvibe.landlog.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NoValidBlogFormException extends RuntimeException{
    private final ErrorCode errorCode;
}
