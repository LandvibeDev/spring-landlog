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

    public Member findById(Long id){
        return memberRepository.findById(id).get();
    }
    public Long join(Member member) throws RuntimeException{
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) throws RuntimeException{
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
                .orElseThrow(() -> new IllegalArgumentException("이메일을 확인해 주세요."));
        validCorrectPassword(logInForm, member);
        return member.getId();
    }

    private void validCorrectPassword (LoginForm loginForm, Member member) throws RuntimeException{
        if (!member.getPassword().equals(loginForm.getPassword())) {
            throw new IllegalArgumentException("비밀번호를 확인해주세요.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

}
