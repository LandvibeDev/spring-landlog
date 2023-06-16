package com.landvibe.landlog.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LandLogException extends RuntimeException {
    private final ErrorCode errorCode;
}
