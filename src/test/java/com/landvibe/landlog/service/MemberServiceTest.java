package com.landvibe.landlog.service;

import com.landvibe.landlog.Message;
import com.landvibe.landlog.form.MemberLoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository;
    Member member = new Member("gildong", "abc@def.com", "1234");

    @BeforeEach
    void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    @DisplayName("정상 회원가입")
    void normalJoin() throws Exception {
        //when
        Long id = memberService.join(member);

        //then
        Member findMember = memberRepository.findById(id).get();
        assertEquals(member.getName(), findMember.getName());
    }

    @Test
    @DisplayName("이름 누락 회원가입")
    void joinWithoutName() throws Exception {
        //given
        Member member = new Member("", "abc@def.com", "1234");

        //when, then
        Exception e = assertThrows(Exception.class,
                () -> memberService.join(member));
        assertThat(e.getMessage()).isEqualTo(Message.NO_INPUT_NAME.message);
    }

    @Test
    @DisplayName("이메일 누락 회원가입")
    void joinWithoutEmail() throws Exception {
        //given
        Member member = new Member("elice", "", "1234");

        //when, then
        Exception e = assertThrows(Exception.class,
                () -> memberService.join(member));
        assertThat(e.getMessage()).isEqualTo(Message.NO_INPUT_EMAIL.message);
    }

    @Test
    @DisplayName("비밀번호 누락 회원가입")
    void joinWithoutPassword() throws Exception {
        //given
        Member member = new Member("july", "abc@def.com", "");

        //when, then
        Exception e = assertThrows(Exception.class,
                () -> memberService.join(member));
        assertThat(e.getMessage()).isEqualTo(Message.NO_INPUT_PASSWORD.message);
    }

    @Test
    @DisplayName("정상 로그인")
    void normalLogin() throws Exception {
        //given
        MemberLoginForm loginForm = new MemberLoginForm();
        loginForm.setEmail("abc@def.com");
        loginForm.setPassword("1234");
        memberService.join(member);

        //when
        Long id = memberService.login(loginForm);

        //then
        Member findMember = memberRepository.findById(id).get();
        assertEquals(id, findMember.getId());
    }

    @Test
    @DisplayName("이름 중복 회원가입")
    void joinWithDuplicateName() throws Exception {
        //given
        memberService.join(member);
        Member anotherMember = new Member("gildong", "qwe@def.com", "1234");

        //when, then
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(anotherMember));
        assertThat(e.getMessage()).isEqualTo(Message.DUPLICATE_NAME.message);
    }

    @Test
    @DisplayName("이메일 중복 회원가입")
    void joinWithDuplicateEmail() throws Exception {
        //given
        memberService.join(member);
        Member anotherMember = new Member("jiwoo", "abc@def.com", "1234");

        //when, then
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(anotherMember));
        assertThat(e.getMessage()).isEqualTo(Message.DUPLICATE_EMAIL.message);
    }
}