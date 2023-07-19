package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.LoginForm;
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

	public Long join(Member member) {
		validateDuplicateMember(member); //중복 회원 검증
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		memberRepository.findByEmail(member.getEmail())
			.ifPresent(m -> {
				throw new IllegalStateException("중복된 이메일입니다.");
			});
	}


	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	public Member findById(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
		return member;
	}

	public Long logIn(LoginForm logInForm) {
		Member member = memberRepository.findByEmail(logInForm.getEmail())
			.orElseThrow(() -> new IllegalArgumentException("잘못된 이메일입니다."));
		if (!member.getPassword().equals(logInForm.getPassword())) {
			throw new IllegalArgumentException("잘못된 비밀번호입니다.");
		}
		return member.getId();
	}
}
