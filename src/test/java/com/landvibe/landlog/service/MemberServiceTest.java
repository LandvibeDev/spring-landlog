package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.exception.LandLogException;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemoryMemberRepository memberRepository;

    @InjectMocks
    MemberService memberService;

    //member
    Long memberId = 1L;
    String name = "spring1";
    String email = "spring1@spring.com";
    String password = "1234";
    Member member = createMember(memberId, name, email, password);

    Member createMember(Long id, String name, String email, String password) {
        return Member.builder()
                .id(id)
                .name(name)
                .email(email)
                .password(password)
                .build();
    }

    @Test
    @DisplayName("join_회원가입_성공")
    void join_whenDetailsProvided_Success() throws Exception {
        //given
        when(memberRepository.save(name, email, password)).thenReturn(member);

        //when
        Long actualMemberId = memberService.join(name, email, password);
        assertThat(actualMemberId).isEqualTo(memberId);

        //Then
        verify(memberRepository).save(name, email, password);
    }


    @Test
    @DisplayName("join_중복이름_예외발생")
    void join_whenDuplicateName_Exception() throws Exception {
        //given
        when(memberRepository.save(name, email, password)).thenThrow(LandLogException.class);

        //when & then
        assertThrows(LandLogException.class,
                () -> memberService.join(name, email, password));

        //Then
        verify(memberRepository, times(1)).save(name, email, password);
    }

    @Test
    @DisplayName("join_중복이메일_예외발생")
    void join_whenDuplicateEmail_Exception() throws Exception {
        //given
        when(memberRepository.save(name, email, password)).thenThrow(LandLogException.class);

        //when & then
        assertThrows(LandLogException.class,
                () -> memberService.join(name, email, password));

        //Then
        verify(memberRepository, times(1)).save(name, email, password);
    }
}