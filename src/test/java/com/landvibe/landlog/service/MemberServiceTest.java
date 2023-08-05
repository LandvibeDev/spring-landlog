package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.form.LoginForm;
import com.landvibe.landlog.form.MemberForm;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.assertj.core.api.AbstractStringAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService = new MemberService(new MemoryMemberRepository());

    String name = "name";
    String password = "password";
    String email = "email";

    String existingMemberMessage = "이미 존재하는 회원입니다.";
    String cantFindMemberMessage = "이메일, 비밀번호가 일치하는 회원이 존재하지 않습니다";
    Long saveId = 1L;
    MemberForm form = MemberForm.builder()
            .email(email)
            .password(password)
            .name(name)
            .build();
    MemberForm duplicateForm = MemberForm.builder()
            .email(email)
            .password(password)
            .name(name)
            .build();

    @BeforeEach
    public void beforeEach() {
        memberService.clearRespository();
    }




    @Test
    public void 회원가입() {
        //Given

        //When
        memberService.join(form);
        System.out.println(saveId);
        //Then
        Member findMember = memberService.findById(saveId);
        assertEquals(form.getName(), findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //Given

        //When
        memberService.join(form);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(duplicateForm));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo(existingMemberMessage);
    }

    @Test
    public void 로그인_일치하는_회원_없음() {
        LoginForm form = LoginForm.builder()
                .email(email)
                .password(password)
                .build();
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.login(form));
        assertThat(e.getMessage()).isEqualTo(cantFindMemberMessage);
    }

    @Test
    public void 로그인_성공() {

        Member member1 = Member.builder()
                .name(name)
                .password(password)
                .email(email)
                .build();

        LoginForm loginForm = LoginForm.builder()
                .email(email)
                .password(password)
                .build();

        memberService.join(form);
        Member login = memberService.login(loginForm);
        Assertions.assertNotNull(login);
    }




}