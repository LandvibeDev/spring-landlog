package com.landvibe.landlog.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IllegalCreatorIdException extends RuntimeException{
    private final ErrorCode errorCode;
}
