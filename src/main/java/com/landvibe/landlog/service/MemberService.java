package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.exception.LoginException;
import com.landvibe.landlog.exception.MemberException;
import com.landvibe.landlog.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.landvibe.landlog.ErrorMessage.*;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member) throws MemberException {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new MemberException(EXIST_MEMBER.message, HttpStatus.CONFLICT);
                });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(NO_MATCH_MEMBER_WITH_CREATOR_ID.message, HttpStatus.NOT_FOUND));
        return member;
    }

    public Long login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new LoginException(WRONG_EMAIL.message, HttpStatus.NOT_FOUND));
        if (!password.equals(member.getPassword())) {
            throw new LoginException(WRONG_PASSWORD.message, HttpStatus.UNAUTHORIZED);
        }
        return member.getId();
    }
}