package com.landvibe.landlog.exceptions;

import com.landvibe.landlog.repository.MemberRepository;
import com.landvibe.landlog.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@Slf4j
public class ExceptionHandling {
    public static String exceptionHandlingMethod(Long creatorId, MemberService memberService) {
        try {
            if (memberService.findOne(creatorId).isEmpty()) {
                throw new IllegalArgumentException("creatorId가 전달되지 않았습니다.");
            }
        } catch (Exception e) {
            log.info("Exception 발생 : " + e.getClass().getName() + ", " + e.getMessage());
            return "home";
        }
        return null;
    }
}
