package com.landvibe.landlog.service;

import com.landvibe.landlog.dto.LoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.exception.MemberException;
import com.landvibe.landlog.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.landvibe.landlog.exception.ExceptionMessage.*;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member;
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new MemberException(ALREADY_EXIST.message, HttpStatus.BAD_REQUEST);
                });
    }

    public boolean isValidCreatorId(Long creatorId) {
        memberRepository.findById(creatorId)
                .orElseThrow(() ->  new MemberException(NO_USER.message, HttpStatus.BAD_REQUEST));
        return true;
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(NO_USER.message, HttpStatus.BAD_REQUEST));
        return member;
    }

    public Long login(LoginForm loginForm) {
        Member member = memberRepository.findByEmail(loginForm.getEmail())
                .orElseThrow(() -> new MemberException(NO_USER.message, HttpStatus.BAD_REQUEST));
        if (!member.getPassword().equals(loginForm.getPassword())) {
            throw new MemberException(WRONG_PASSWORD.message, HttpStatus.BAD_REQUEST);
        }
        return member.getId();
    }
}
