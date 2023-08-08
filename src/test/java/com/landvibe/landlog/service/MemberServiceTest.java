package com.landvibe.landlog.service;

import com.landvibe.landlog.Message;
import com.landvibe.landlog.form.MemberLoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    void 회원가입_정상() throws Exception {
        //when
        Long id = memberService.join(member);

        //then
        Member findMember = memberRepository.findById(id).get();
        assertEquals(member.getName(), findMember.getName());
    }

    @Test
    void 회원가입_이름_누락() throws Exception {
        //given
        Member member = new Member("", "abc@def.com", "1234");

        //when, then
        Exception e = assertThrows(Exception.class,
                () -> memberService.join(member));
        assertThat(e.getMessage()).isEqualTo(Message.NO_INPUT_NAME.message);
    }

    @Test
    void 회원가입_이메일_누락() throws Exception {
        //given
        Member member = new Member("elice", "", "1234");

        //when, then
        Exception e = assertThrows(Exception.class,
                () -> memberService.join(member));
        assertThat(e.getMessage()).isEqualTo(Message.NO_INPUT_EMAIL.message);
    }

    @Test
    void 회원가입_비밀번호_누락() throws Exception {
        //given
        Member member = new Member("july", "abc@def.com", "");

        //when, then
        Exception e = assertThrows(Exception.class,
                () -> memberService.join(member));
        assertThat(e.getMessage()).isEqualTo(Message.NO_INPUT_PASSWORD.message);
    }

    @Test
    void 로그인_정상() throws Exception {
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
    void 중복_이름_예외() throws Exception {
        //given
        memberService.join(member);
        Member anotherMember = new Member("gildong", "qwe@def.com", "1234");

        //when, then
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(anotherMember));
        assertThat(e.getMessage()).isEqualTo(Message.DUPLICATE_NAME.message);
    }

    @Test
    void 중복_이메일_예외() throws Exception {
        //given
        memberService.join(member);
        Member anotherMember = new Member("jiwoo", "abc@def.com", "1234");

        //when, then
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(anotherMember));
        assertThat(e.getMessage()).isEqualTo(Message.DUPLICATE_EMAIL.message);
    }
}