package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.form.LoginForm;
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

    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
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
        return optionalMember.orElseThrow(() -> new IllegalStateException("아이디와 일치하는 회원을 찾을 수 없습니다."));
    }

    public Member login(LoginForm form) {
        String email = form.getEmail();
        String password = form.getPassword();
        Optional<Member> optionalMember = memberRepository.findByEmailAndPassword(email, password);
        return optionalMember.orElseThrow(() -> new IllegalStateException("이메일, 비밀번호가 일치하는 회원이 존재하지 않습니다"));
    }

    public void clearRespository() {
        memberRepository.clear();

    }
}