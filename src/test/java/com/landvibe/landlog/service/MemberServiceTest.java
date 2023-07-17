package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.form.LoginForm;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    String name="name";
    String password="password";
    String email="email";

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
        Member member = new Member();
        member.setName(name);
        //When
        Long saveId = memberService.join(member);
        //Then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }
    @Test
    public void 중복_회원_예외() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setName(name);
        Member member2 = new Member();
        member2.setName(name);
        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    public void 로그인_일치하는_회원_없음()throws Exception{
        LoginForm form=new LoginForm();
        form.setEmail(email);
        form.setPassword(password);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.login(form));
        Assertions.assertEquals(e.getMessage(), "이메일, 비밀번호가 일치하는 회원이 존재하지 않습니다");
    }
    @Test
    public void 로그인_성공(){

        Member member1 = new Member();
        member1.setName(name);
        member1.setEmail(email);
        member1.setPassword(password);

        LoginForm form=new LoginForm();
        form.setEmail(email);
        form.setPassword(password);

        memberService.join(member1);
        Member login = memberService.login(form);
        Assertions.assertNotNull(login);
    }



}