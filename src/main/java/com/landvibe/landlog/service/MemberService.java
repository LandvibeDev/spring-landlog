package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.LoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemberRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member) throws RuntimeException{
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다.");
                });

        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m -> {
                    throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
                });
    }

    public Long logIn(LoginForm logInForm) throws RuntimeException{
        Member member = memberRepository.findByEmail(logInForm.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 이메일입니다."));
        validCorrectPassword(logInForm, member);
        return member.getId();
    }

    private void validCorrectPassword(LoginForm loginForm, Member member){
        if (!member.getPassword().equals(loginForm.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

}
