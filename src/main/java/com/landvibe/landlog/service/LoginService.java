package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.LoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.exception.InvalidEmailException;
import com.landvibe.landlog.exception.PasswordMismatchException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final MemberService memberService;

    public LoginService(MemberService memberService) {
        this.memberService = memberService;
    }

    public Long login(LoginForm loginForm) throws RuntimeException {
        Optional<Member> member = memberService.findOneByEmail(loginForm.getEmail());
        validateLogin(member, loginForm);

        return member.get().getId();
    }

    private void validateLogin(Optional<Member> member, LoginForm loginForm) throws RuntimeException {
        checkPassword(member.orElseThrow(() -> new InvalidEmailException("존재하지 않는 이메일입니다.")), loginForm);
    }

    private void checkPassword(Member member, LoginForm loginForm) {
        if (!member.getPassword().equals(loginForm.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
    }
}
