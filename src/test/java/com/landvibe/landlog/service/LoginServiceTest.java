package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.exception.LandLogException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    MemberService memberService;

    @InjectMocks
    LoginService loginService;

    // member
    Long memberId = 1L;
    String name = "spring";
    String email = "spring@spring.com";
    String password = "1234";

    //error
    String errorPassword = "error";


    Member createMember(Long id, String name, String email, String password) {
        Member member = Member.createMember(name, email, password);
        member.setId(id);

        return member;
    }

    @Test
    @DisplayName("login_로그인_정상수행")
    void login_whenNormalInput_success() {
        //given
        Member member = createMember(memberId, name, email, password);
        when(memberService.findMemberByEmail(eq(email))).thenReturn(member);

        //when & then
        Long actual = loginService.login(email, password);
        assertThat(actual).isEqualTo(memberId);

        //then
        verify(memberService).findMemberByEmail(eq(email));
    }

    @Test
    @DisplayName("login_이메일존재X_에러")
    void login_whenEmailIsNotExist_Exception() {
        //given
        when(memberService.findMemberByEmail(anyString())).thenThrow(LandLogException.class);

        //when & then
        assertThrows(LandLogException.class,
                () -> loginService.login(email, password));

        //then
        verify(memberService, times(1)).findMemberByEmail(anyString());
    }

    @Test
    @DisplayName("login_비밀번호불일치_에러")
    void login_whenPasswordIsNotCorrect_Exception() {
        //given
        Member member = createMember(memberId, name, email, password);
        when(memberService.findMemberByEmail(email)).thenReturn(member);

        //when & then
        assertThrows(LandLogException.class,
                () -> loginService.login(email, errorPassword));

        //then
        verify(memberService).findMemberByEmail(email);
    }
}
