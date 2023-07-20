package com.landvibe.landlog.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionHandling {
    public static String exceptionHandlingMethod(Long creatorId) {
        try {
            if(creatorId == null) throw new IllegalArgumentException("creatorId가 전달되지 않았습니다.");
        } catch (Exception e) {
            log.info("Exception 발생 : " + e.getClass().getName() + ", " + e.getMessage());
            return "home";
        }
        return null;
    }
}
