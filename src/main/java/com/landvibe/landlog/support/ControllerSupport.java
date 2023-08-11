package com.landvibe.landlog.support;

import com.landvibe.landlog.exception.LoginFailException;
import com.landvibe.landlog.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URI;

@ControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ControllerSupport {

    @ExceptionHandler({MemberNotFoundException.class, LoginFailException.class})
    public String handle(RuntimeException e) {
        System.out.println(e.getMessage());
        return "home";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handle(IllegalStateException e) {
        System.out.println(e.getMessage());
        return "members/createMemberForm";
    }


}
