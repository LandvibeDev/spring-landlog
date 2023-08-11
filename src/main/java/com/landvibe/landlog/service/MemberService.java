package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.exception.LoginFailException;
import com.landvibe.landlog.exception.MemberNotFoundException;
import com.landvibe.landlog.form.LoginForm;
import com.landvibe.landlog.form.MemberForm;
import com.landvibe.landlog.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(MemberForm form) {
        String name = form.getName();
        String email = form.getEmail();
        String password = form.getPassword();
        Member member = Member.builder()
                .name(name)
                .password(password)
                .email(email)
                .build();
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }


    public List<Member> findMembers() {
        return memberRepository.findAll();
    }


    public Member findById(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        return optionalMember.orElseThrow(() -> new MemberNotFoundException("아이디와 일치하는 회원을 찾을 수 없습니다."));
    }//완료

    public Member login(LoginForm form) {
        String email = form.getEmail();
        String password = form.getPassword();
        Optional<Member> optionalMember = memberRepository.findByEmailAndPassword(email, password);
        return optionalMember.orElseThrow(() -> new LoginFailException("이메일, 비밀번호가 일치하는 회원이 존재하지 않습니다"));
    }

    public void clearRespository() {
        memberRepository.clear();

    }
}