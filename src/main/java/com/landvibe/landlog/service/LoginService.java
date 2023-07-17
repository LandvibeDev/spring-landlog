package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.LoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final MemberRepository memberRepository;

    @Autowired // 생략 가능
    public LoginService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long login(LoginForm loginForm) throws IllegalArgumentException {
        validateLogin(loginForm);
        return memberRepository.findByEmail(loginForm.getEmail()).get().getId();
    }

    private void validateLogin(LoginForm loginForm) {
        Optional<Member> member = memberRepository.findByEmail(loginForm.getEmail());

        if (member.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 이메일 입니다.");
        }

        member.ifPresent(m -> {
            if (!m.getPassword().equals(loginForm.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        });
    }
}
