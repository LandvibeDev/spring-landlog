package com.landvibe.landlog.service;

import com.landvibe.landlog.Message;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.form.MemberLoginForm;
import com.landvibe.landlog.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member) {
        validateEmptyInput(member);
        validateDuplicateName(member);
        validateDuplicateEmail(member);
        memberRepository.save(member);
        return member.getId();
    }

    public Long login(MemberLoginForm form) {
        Member member = memberRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(Message.NO_USER.message));
        if (!member.getPassword().equals(form.getPassword())) {
            throw new IllegalArgumentException(Message.NO_USER.message);
        }
        return member.getId();
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(Message.NO_USER.message));
        return member;
    }

    private void validateDuplicateEmail(Member member) {
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m -> {
                    throw new IllegalArgumentException(Message.DUPLICATE_EMAIL.message);
                });
    }

    private void validateDuplicateName(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalArgumentException(Message.DUPLICATE_NAME.message);
                });
    }

    private void validateEmptyInput(Member member) {
        if (member.getName().equals("")) {
            throw new IllegalArgumentException(Message.NO_INPUT_NAME.message);
        }
        if (member.getEmail().equals("")) {
            throw new IllegalArgumentException(Message.NO_INPUT_EMAIL.message);
        }
        if (member.getPassword().equals("")) {
            throw new IllegalArgumentException(Message.NO_INPUT_PASSWORD.message);
        }
    }
}
