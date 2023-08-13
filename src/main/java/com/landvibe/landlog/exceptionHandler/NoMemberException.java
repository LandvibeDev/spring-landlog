package com.landvibe.landlog.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NoMemberException extends RuntimeException{
    private final ErrorCode errorCode;
}
