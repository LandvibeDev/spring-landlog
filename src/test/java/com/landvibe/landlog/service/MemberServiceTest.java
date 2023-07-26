package com.landvibe.landlog.service;

import com.landvibe.landlog.form.LoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;
    Member member1 = new Member("철수", "cs@inha.com", "1234");

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
    @DisplayName("회원가입 성공")
    public void join() {
        Member member = new Member();
        member.setName("hello");

        Long saveId = memberService.join(member);

        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }

    @Test
    @DisplayName("회원가입 실패 : 중복 회원 예외처리")
    public void validateDuplicateMember() {
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");

        memberService.join(member1);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    @DisplayName("ID로 회원 찾기")
    void findById() {
        memberRepository.save(member1);

        memberRepository.save(member1);

        Member result = memberRepository.findById(member1.getId()).get();

        Assertions.assertThat(result).isEqualTo(member1);
    }

    @Test
    @DisplayName("회원 목록 조회")
    public void findAll() {
        memberRepository.save(member1);
        Member member2 = new Member("영희", "yh@inha.com", "5678");
        memberRepository.save(member2);
        Member member3 = new Member("길동", "gd.inha.com", "789");
        memberRepository.save(member3);
        List<Member> result = memberRepository.findAll();

        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("로그인 성공")
    void logIn() {
        Member member = new Member("양재승", "jaeseung@naver.com", "123");

        memberService.join(member);

        LoginForm loginForm = new LoginForm("jaeseung@naver.com", "123");

        Long loginId = memberService.logIn(loginForm);
        assertThat(loginId).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("로그인 실패 : 틀린 비밀번호 입력")
    public void validCorrectPassword_Test(){
        String testEmail = "jaeseung@naver.com";
        Member member = new Member("양재승", testEmail, "123");

        memberService.join(member);
        LoginForm loginForm = new LoginForm(testEmail, "456");

        Exception e = assertThrows(Exception.class,
                () -> memberService.logIn(loginForm));
        assertThat(e.getMessage()).isEqualTo("비밀번호를 확인해주세요.");
    }

}