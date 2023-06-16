package com.landvibe.landlog.service;

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
        member.setEmail("email");
        member.setPassword("pass");
        //When
        Long saveId = memberService.join(member);
        //Then
        Member actual = memberRepository.findById(saveId).get();
        assertEquals(member, actual);
    }

    @Test
    public void 회원가입_중복_이름_예외() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setName("spring");
        member1.setEmail("email1");
        member1.setPassword("pass1");
        Member member2 = new Member();
        member2.setName("spring");
        member2.setEmail("email2");
        member2.setPassword("pass2");
        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    public void 회원가입_중복_이메일_예외() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setName("spring1");
        member1.setEmail("email");
        member1.setPassword("pass1");
        Member member2 = new Member();
        member2.setName("spring2");
        member2.setEmail("email");
        member2.setPassword("pass2");

        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 이메일입니다.");
    }

    @Test
    public void 로그인() throws Exception {
        //Given
        String email = "email";
        String password = "pass";
        Member member = new Member(0L, "hello", email, password);
        memberService.join(member);

        //When
        Member actual = memberService.login(email, password);

        //Then
        assertEquals(member, actual);
    }

    @Test
    public void 로그인_이메일_없음_예외() throws Exception {
        //Given
        String email = "email";
        String password = "pass";
        Member member = new Member(0L, "hello", email, password);
        memberService.join(member);

        //When
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.login("noemail", password));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 회원입니다.");
    }

    @Test
    public void 로그인_패스워드_틀림_예외() throws Exception {
        //Given
        String email = "email";
        String password = "pass";
        Member member = new Member(0L, "hello", email, password);
        memberService.join(member);

        //When
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.login(email, "nopass"));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("패스워드가 틀렸습니다.");
    }
}