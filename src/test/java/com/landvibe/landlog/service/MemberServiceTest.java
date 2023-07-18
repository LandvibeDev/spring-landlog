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

    MemoryMemberRepository memberRepository;
    MemberService memberService;

    //member
    String name = "spring1";
    String email = "spring1@spring.com";
    String password = "1234";

    //different
    String differentName = "spring2";
    String differentEmail = "spring2@spring.com";
    String differentPassword = "12345";


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
        Member member = new Member(name, email, password);

        //When
        Long saveId = memberService.join(member);

        //Then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }

    @Test
    public void 중복_이름_예외() throws Exception {
        //Given
        Member member = new Member(name, email, password);
        Member sameNameMember = new Member(name, differentEmail, differentPassword);

        //When
        memberService.join(member);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(sameNameMember));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 이름입니다.");
    }

    @Test
    public void 중복_이메일_예외() throws Exception {
        //Given
        Member member = new Member(name, email, password);
        Member sameEmailMember = new Member(differentName, email, differentEmail);

        //When
        memberService.join(member);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(sameEmailMember));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 이메일입니다.");
    }
}