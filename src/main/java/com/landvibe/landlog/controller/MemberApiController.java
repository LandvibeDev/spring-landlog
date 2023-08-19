package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.dto.LoginForm;
import com.landvibe.landlog.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("v1/api/members")
@RestController
public class MemberApiController {
    private final MemberService memberService;

    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public Member create(@RequestBody Member member) {
        return memberService.join(member);
    }

    @GetMapping
    public List<Member> list() {
        return memberService.findMembers();
    }

    @PostMapping(value = "/login")
    public Long login(@RequestBody LoginForm loginForm) {
        return memberService.login(loginForm);
    }
}
