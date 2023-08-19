package com.landvibe.landlog.exceptionHandler;

import lombok.Builder;

@Builder
public class ErrorResponse {
    int status;
    String message;
}
