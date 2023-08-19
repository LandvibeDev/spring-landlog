package com.landvibe.landlog.service;

import com.landvibe.landlog.dto.LoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.exception.MemberException;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.landvibe.landlog.exception.BaseException.NO_USER;
import static com.landvibe.landlog.exception.BaseException.WRONG_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
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
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");
        //When
        memberService.join(member1);
        MemberException e = assertThrows(MemberException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
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

        //when
        memberService.join(member);
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