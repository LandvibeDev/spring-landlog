package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.LoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

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
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member("soohwan","hohoho@landvibe.com","1234");
        //When
        Long saveId = memberService.join(member);
        //Then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }
    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member("dongha","hohoho@landvibe.com","1234");
        Member member2 = new Member("seungcheol","hohoho@landvibe.com","4567");
        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("중복된 이메일입니다.");
    }

    @Test
    public void 로그인_성공() {
        //given
        Member member = new Member("Junyeong", "jyp@landvibe.com", "1234");

        //when
        memberService.join(member);
        LoginForm loginForm = new LoginForm( "jyp@landvibe.com", "1234");

        //then
        Long memberId = memberService.logIn(loginForm);
        assertThat(memberId).isEqualTo(member.getId());
    }

    @Test
    public void 로그인_실패_잘못된이메일() {
        //given
        Member member = new Member("Jaeseung", "jaewin@landvibe.com", "4567");

        //when
        memberService.join(member);
        LoginForm loginForm = new LoginForm("zoomin@landvibe.com", "4567");

        //then
        Exception e = assertThrows(Exception.class,
            () -> memberService.logIn(loginForm));
        assertThat(e.getMessage()).isEqualTo("잘못된 이메일입니다.");
    }

    @Test
    public void 로그인_성공_잘못된비밀번호(){
        //given
        Member member = new Member("sewon", "sewon@landvibe.com", "3434");

        //when
        memberService.join(member);
        LoginForm loginForm = new LoginForm("sewon@landvibe.com", "4545");

        //then
        Exception e = assertThrows(Exception.class,
            () -> memberService.logIn(loginForm));
        assertThat(e.getMessage()).isEqualTo("잘못된 비밀번호입니다.");
    }
}