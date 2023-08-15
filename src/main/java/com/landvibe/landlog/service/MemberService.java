package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.exception.LandLogException;
import com.landvibe.landlog.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.landvibe.landlog.exception.ErrorCode.*;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // <--생성-->
    public Long join(String name, String email, String password) {
        validateDuplicateMember(name, email); // 예외
        Member member = memberRepository.save(name, email, password);
        return member.getId();
    }

    //<--조회-->
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new LandLogException(NOT_FOUND_USER));
        return member;
    }

    public Member findMemberByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new LandLogException(NOT_FOUND_USER));
        return member;
    }

    // <--valid-->
    private void validateDuplicateMember(String name, String email) {
        memberRepository.findByName(name)
                .ifPresent(m -> {
                    throw new LandLogException(DUPLICATE_USER_NAME);
                });

        memberRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new LandLogException(DUPLICATE_USER_EMAIL);
                });
    }

}
