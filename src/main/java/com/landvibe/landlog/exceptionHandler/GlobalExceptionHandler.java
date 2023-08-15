package com.landvibe.landlog.exceptionHandler;

import com.landvibe.landlog.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //MemberService
    @ExceptionHandler({DuplicateSignUpInfoException.class})
    protected ResponseEntity<?> handleDuplicateSignUpInfoException(DuplicateSignUpInfoException e) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("회원가입에 실패했습니다.")
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler({NoValidLoginException.class}) //로그인 틀린 값 예외(이메일, 비밀번호)
    protected ResponseEntity<?> handleNoValidLoginException(NoValidLoginException e) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("로그인에 실패했습니다.")
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
    }

    @ExceptionHandler({NoMemberException.class}) //등록된 회원 없음 예외
    protected ResponseEntity<?> handleNoMemberException(NoMemberException e) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("등록된 회원이 없습니다.")
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    //blogService
    @ExceptionHandler()
    protected ResponseEntity<?> handleIllegalCreatorIdException(IllegalCreatorIdException e) {
        log.error(e.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code("회원 정보가 없습니다.")
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler()
    protected ResponseEntity<?> handleIllegalBlogIdException(IllegalBlogIdException e) {
        log.error(e.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code("블로그 정보가 없습니다.")
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler()
    protected ResponseEntity<?> handleNoValidBlogFormException(NoValidBlogFormException e) {
        log.error(e.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code("내용을 확인해 주세요.")
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(errorResponse);
    }
}
