package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.LoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    MemoryMemberRepository memberRepository;
    LoginService loginService;

    // member, loginForm
    String name = "spring";
    String email = "spring@spring.com";
    String password = "1234";
    LoginForm loginForm = new LoginForm(email, password);

    //error
    String errorEmail = "error@spring.com";
    String errorPassword = "error";


    @BeforeEach
    void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        loginService = new LoginService(memberRepository);
    }

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    @DisplayName("로그인_정상")
    void login() {
        //given
        Member member = new Member(name, email, password);
        memberRepository.save(member);

        //when
        Long memberId = loginService.login(loginForm);

        //then
        Assertions.assertThat(member.getId()).isEqualTo(memberId);
    }

    @Test
    @DisplayName("로그인_비정상_이메일_존재X")
    void login_whenEmailIsNotExist_Exception() {
        //given
        Member member = new Member(name, email, password);
        memberRepository.save(member);
        LoginForm errorLoginForm = new LoginForm(errorEmail, password);


        //when & then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> loginService.login(errorLoginForm));
        Assertions.assertThat(e.getMessage()).isEqualTo("존재하지 않는 이메일입니다.");
    }

    @Test
    @DisplayName("로그인_비정상_비밀번호_불일치")
    void login_whenPasswordIsNotCorrect_Exception() {
        //given
        Member member = new Member(name, email, password);
        memberRepository.save(member);
        LoginForm errorLoginForm = new LoginForm(email, errorPassword);

        //when & then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> loginService.login(errorLoginForm));
        Assertions.assertThat(e.getMessage()).isEqualTo("비밀번호가 일치하지 않습니다.");
    }
}