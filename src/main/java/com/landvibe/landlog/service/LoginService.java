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

    public LoginService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long login(LoginForm loginForm) throws IllegalArgumentException {
        Optional<Member> member = memberRepository.findByEmail(loginForm.getEmail());
        Member validMember = validateLogin(member, loginForm);

        return validMember.getId();
    }

    private Member validateLogin(Optional<Member> member, LoginForm loginForm) {
        Member validMember = member.orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않는 이메일 입니다.");
        });

        if (!validMember.getPassword().equals(loginForm.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return validMember;
    }
}
