package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.exception.ErrorCode;
import com.landvibe.landlog.exception.LandLogException;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final MemberService memberService;

    public LoginService(MemberService memberService) {
        this.memberService = memberService;
    }

    public Long login(String email, String password) throws RuntimeException {
        Member member = memberService.findMemberByEmail(email); // 예외
        validatePassword(member, password);

        return member.getId();
    }

    private void validatePassword(Member member, String password) throws RuntimeException {
        if (!member.getPassword().equals(password)) {
            throw new LandLogException(ErrorCode.NOT_FOUND_USER);
        }
    }
}
