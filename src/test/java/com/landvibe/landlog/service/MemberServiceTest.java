package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.MemberForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    MemberService memberService = new MemberService(memberRepository);

    MemberForm memberForm1 = new MemberForm("spring", "spring1@spring.com", "1234");
    MemberForm memberForm1SameName = new MemberForm("spring", "spring2@spring.com", "1234");

    MemberForm memberForm2 = new MemberForm("spring1", "spring@spring.com", "1234");
    MemberForm memberForm2SameEmail = new MemberForm("spring2", "spring@spring.com", "1234");

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member(memberForm1);

        //When
        Long saveId = memberService.join(member);

        //Then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }

    @Test
    public void 중복_이름_예외() throws Exception {
        //Given
        Member member1 = new Member(memberForm1);
        Member member2 = new Member(memberForm1SameName);

        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 이름입니다.");
    }

    @Test
    public void 중복_이메일_예외() throws Exception {
        //Given
        Member member1 = new Member(memberForm2);
        Member member2 = new Member(memberForm2SameEmail);

        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 이메일입니다.");
    }
}