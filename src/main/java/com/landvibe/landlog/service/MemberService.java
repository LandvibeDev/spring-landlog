package com.landvibe.landlog.service;

import com.landvibe.landlog.form.LoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findMemberById(Long id) {
        validNoMember();
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id 입니다."));
        return member;
    }

    public Long join(Member member) {
        validateDuplicateMember(member);

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

    public Long logIn(LoginForm logInForm) {
        Member member = memberRepository.findByEmail(logInForm.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일을 확인해 주세요."));
        validCorrectPassword(logInForm, member);
        validNoMember();
        return member.getId();
    }

    private void validCorrectPassword(LoginForm loginForm, Member member) {
        if (!member.getPassword().equals(loginForm.getPassword())) {
            throw new IllegalArgumentException("비밀번호를 확인해주세요.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    private void validNoMember() {
        if (memberRepository.noMember()) {
            throw new IllegalArgumentException("등록된 회원이 없습니다.");
        }
    }
}
