package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.landvibe.landlog.ErrorMessage.*;
import java.util.Optional;

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
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setName("hello");
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
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");
        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo(EXIST_MEMBER.message);
    }

    private Member createMember() {
        Member member = new Member("name", "email", "password");
        memberService.join(member);
        return member;
    }

    @Test
    @DisplayName("[로그인 성공] 이메일, 패스워드 일치")
    public void login_success() {
        //Given
        Member member = createMember();

        //When
        Long successResult = memberService.login("email", "password");

        //Then
        assertThat(successResult).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("[로그인 실패] 이메일 불일치")
    public void login_fail_wrong_email() {
        //
        Member member = createMember();

        //
        Exception exception = assertThrows(Exception.class, () -> {
            memberService.login("wrong_email", "password");
        });
        assertThat(exception.getMessage()).isEqualTo(WRONG_EMAIL.message);
    }

    @Test
    @DisplayName("[로그인 실패] 패스워드 불일치")
    public void login_fail_wrong_password() {
        //
        Member member = createMember();

        //
        Exception exception = assertThrows(Exception.class, () -> {
            memberService.login("email", "wrong_password");
        });
        assertThat(exception.getMessage()).isEqualTo(WRONG_PASSWORD.message);
    }

    private Member createMember() {
        Member member1 = new Member();
        member1.setName("name1");
        member1.setEmail("email1");
        member1.setPassword("password1");
        memberService.join(member1);
        return member1;
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void login_success(){
        //Given
        Member member1 = createMember();

        //When
        Optional<Member> successResult = memberService.login("email1", "password1");

        //Then
        assertThat(successResult.get()).isEqualTo(member1);
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void login_fail(){
        //Given
        Member member1 = createMember();

        //When
        Optional<Member> failedResult = memberService.login("email2", "password2");

        //Then
        assertThat(failedResult).isEmpty();
    }
}