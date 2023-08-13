package com.landvibe.landlog.service;

import com.landvibe.landlog.exceptionHandler.DuplicateLoginInfoException;
import com.landvibe.landlog.exceptionHandler.IllegalCreatorIdException;
import com.landvibe.landlog.exceptionHandler.NoMemberException;
import com.landvibe.landlog.exceptionHandler.NoValidPasswordException;
import com.landvibe.landlog.form.LoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemberRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.landvibe.landlog.exceptionHandler.ErrorCode.*;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findMemberById(Long id) {
        validNoMember();

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalCreatorIdException(NO_MATCH_MEMBERID_EXCEPTION));
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
                    throw new DuplicateLoginInfoException(DUPLICATE_NAME_SIGNUP_EXCEPTION);
                });

        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m -> {
                    throw new DuplicateLoginInfoException(DUPLICATE_EMAIL_SIGNUP_EXCEPTION);
                });
    }

    public Long logIn(LoginForm logInForm) {
        Member member = memberRepository.findByEmail(logInForm.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(NO_EXIST_EMAIL_LOGIN_EXCEPTION.getExceptionMessage()));
        validCorrectPassword(logInForm, member);
        validNoMember();
        return member.getId();
    }

    private void validCorrectPassword(LoginForm loginForm, Member member) {
        if (!member.getPassword().equals(loginForm.getPassword())) {
            throw new NoValidPasswordException(INCORRECT_PASSWORD_EXCEPTION);
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    private void validNoMember() {
        if (memberRepository.noMember()) {
            throw new NoMemberException(NO_ONE_MEMBER_EXCEPTION);
        }
    }
}
