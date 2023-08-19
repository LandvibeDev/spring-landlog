package com.landvibe.landlog.exceptionHandler;

import com.landvibe.landlog.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({BlogException.class})
    protected ResponseEntity<?> handleBlogException(BlogException e){
        log.error(e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(e.getErrorCode().getStatus())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorResponse);
    }

    @ExceptionHandler({MemberException.class})
    protected ResponseEntity<?> handleMemberException(MemberException e){
        log.error(e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(e.getErrorCode().getStatus())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorResponse);
    }
}
