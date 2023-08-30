package com.landvibe.landlog.service;

import com.landvibe.landlog.dto.LoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.exception.BlogException;
import com.landvibe.landlog.exception.MemberException;

import com.landvibe.landlog.repository.DBMemberRepository;
import com.landvibe.landlog.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.landvibe.landlog.exception.BaseException.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;
    @Mock
    MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberService = new MemberService(memberRepository);
    }


    @Test
    public void 회원가입() {
        //Given
        Member member = new Member();
        member.setName("hello");
        //When
        Member findMember = memberService.join(member);
        //Then
        assertEquals(member, findMember);
    }

    @Test
    public void 중복_회원_예외() {
        //Given
        Member member = new Member();
        member.setName("spring");

        Mockito.when(memberRepository.findByName("spring"))
                .thenThrow(new MemberException(ALREADY_EXIST));
        //When
        MemberException e = assertThrows(MemberException.class,
                () -> memberService.join(member));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    public void 없는_이메일_예외() {
        //Given
        Member member = Member.builder()
                .name("오형석")
                .password("1234")
                .email("123@naver.com")
                .build();

        //when
        memberService.join(member);
        LoginForm loginForm = LoginForm.builder()
                .email("123@inha.edu")
                .password("123")
                .build();

        //then
        MemberException e = assertThrows(MemberException.class,
                () -> memberService.login(loginForm));
        assertThat(e.getMessage()).isEqualTo(NO_USER.message);
    }

    @Test
    public void 로그인_성공() {
        //Given
        Member member = Member.builder()
                .name("오형석")
                .password("1234")
                .email("123@naver.com")
                .build();

        Mockito.when(memberRepository.findByEmail("123@naver.com"))
                .thenReturn(Optional.ofNullable(member));
        //when
        LoginForm loginForm = LoginForm.builder()
                .email("123@naver.com")
                .password("1234")
                .build();

        //then
        Long memberId = memberService.login(loginForm);
        assertThat(memberId).isEqualTo(member.getId());
    }

    @Test
    public void 틀린_비밀번호() {
        //Given
        Member member = Member.builder()
                .name("오형석")
                .password("1234")
                .email("123@naver.com")
                .build();
        Mockito.when(memberRepository.findByEmail("123@naver.com"))
                .thenReturn(Optional.ofNullable(member));

        //when
        memberService.join(member);
        LoginForm loginForm = LoginForm.builder()
                .email("123@naver.com")
                .password("123")
                .build();

        //then
        MemberException e = assertThrows(MemberException.class,
                () -> memberService.login(loginForm));
        assertThat(e.getMessage()).isEqualTo(WRONG_PASSWORD.message);
    }
}