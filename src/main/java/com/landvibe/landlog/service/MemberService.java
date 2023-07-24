package com.landvibe.landlog.service;

import com.landvibe.landlog.dto.LoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.landvibe.landlog.ErrorMessage.*;

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
                    throw new IllegalStateException(ALREADY_EXIST.message);
                });
    }

    public boolean isValidCreatorId(Long creatorId) {
        memberRepository.findById(creatorId)
                .orElseThrow(() -> new IllegalArgumentException(NO_USER.message));
        return true;
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(NO_USER.message));
        return member;
    }

    public Long login(LoginForm loginForm) {
        Member member = memberRepository.findByEmail(loginForm.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(NO_USER.message));
        if (!member.getPassword().equals(loginForm.getPassword())) {
            throw new IllegalArgumentException(WRONG_PASSWORD.message);
        }
        return member.getId();
    }
}
