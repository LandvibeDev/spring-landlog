package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.LoginForm;
import com.landvibe.landlog.controller.MemberForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    LoginService loginService = new LoginService(memberRepository);


    MemberForm memberForm = new MemberForm("spring", "spring1@spring.com", "1234");
    LoginForm loginForm = new LoginForm("spring1@spring.com", "1234");

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    @DisplayName("로그인_정상")
    void login() {
        //given
        Member member = new Member(memberForm); // "spring", "spring1@spring.com", "1234"
        memberRepository.save(member);

        //when
        Long memberId = loginService.login(loginForm); // "spring1@spring.com", "1234"

        //then
        Assertions.assertThat(member.getId()).isEqualTo(memberId);
    }

    @Test
    @DisplayName("로그인_비정상_이메일_존재X")
    void login_whenEmailIsNotExist_Exception() {
        //given
        Member member = new Member(memberForm); // "spring", "spring1@spring.com", "1234"
        memberRepository.save(member);
        LoginForm errorLoginForm = new LoginForm("error@spring.com", "1234");


        //when & then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> loginService.login(errorLoginForm));
        Assertions.assertThat(e.getMessage()).isEqualTo("존재하지 않는 이메일 입니다.");
    }

    @Test
    @DisplayName("로그인_비정상_비밀번호_불일치")
    void login_whenPasswordIsNotCorrect_Exception() {
        //given
        Member member = new Member(memberForm); // "spring", "spring1@spring.com", "1234"
        memberRepository.save(member);
        LoginForm errorLoginForm = new LoginForm("spring1@spring.com", "error");

        //when & then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> loginService.login(errorLoginForm));
        Assertions.assertThat(e.getMessage()).isEqualTo("비밀번호가 일치하지 않습니다.");
    }
}