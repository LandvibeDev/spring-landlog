package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.form.LoginForm;
import com.landvibe.landlog.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/members")
public class MemberApiController {
    private final MemberService memberService;

    public MemberApiController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping
    public List<Member> list(){
        return memberService.findMembers();
    }

    @PostMapping()
    public Long create(@RequestBody Member member) {
        return memberService.join(member);
    }

    @PostMapping(value = "/login")
    public Long login(@RequestBody LoginForm form) {
        return memberService.logIn(form);
    }
}
