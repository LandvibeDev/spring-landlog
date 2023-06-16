package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.error.ErrorCode;
import com.landvibe.landlog.error.LandLogException;
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
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new LandLogException(ErrorCode.DUPLICATE_USER_NAME);
                });
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m -> {
                    throw new LandLogException(ErrorCode.DUPLICATE_USER_EMAIL);
                });
    }

    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new LandLogException(ErrorCode.NOT_FOUND_USER));
        if (!member.getPassword().equals(password)) {
            throw new LandLogException(ErrorCode.INVALID_PASSWORD);
        }
        return member;
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
