package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        Member member = Member.builder()
                        .name("hello")
                        .build();
        //When
        Long saveId = memberService.join(member);
        //Then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //Given
        Member member1 = Member.builder()
                        .name("spring")
                        .build();
        Member member2 = Member.builder()
                        .name("spring")
                        .build();
        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    @DisplayName("로그인 성공")
    public void login_whenSuchMemberInRepository_thenReturnTrue() {
        //given
        Member member1 = Member.builder().build();
        createNewMember(member1);

        memberService.join(member1);

        Member loginMember = Member.builder()
                .id(1L)
                .name("aaN")
                .email("aaE")
                .password("aaP")
                .build();

        //when
        Optional<Member> logined = memberService.login(loginMember.getEmail(), loginMember.getPassword());

        //then
        assertThat(logined.get()).isEqualTo(member1);
    }

    private static void createNewMember(Member member1) {
        member1.setName("aaN");
        member1.setEmail("aaE");
        member1.setPassword("aaP");
    }

    @Test
    @DisplayName("로그인실패")
    public void login_whenNoSuchMemberInRepository_thenReturnTrue() {
        //given
        Member member1 = Member.builder().build();
        createNewMember(member1);

        memberService.join(member1);

        Member loginMember = Member.builder()
                .id(1L)
                .name("bbN")
                .email("bbE")
                .password("bbP")
                .build();

        //when
        Optional<Member> logined = memberService.login(loginMember.getEmail(), loginMember.getPassword());

        //then
        assertThat(logined).isEmpty();
    }
}
