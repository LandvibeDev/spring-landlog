package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.form.MemberLoginForm;
import com.landvibe.landlog.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/members")
public class MemberApiController {

    private final MemberService memberService;

    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping()
    public List<Member> list() {
        List<Member> members = memberService.findMembers();
        return members;
    }

    @PostMapping()
    public Long create(@RequestBody Member member) {
        Long join = memberService.join(member);
        return join;
    }

    @PostMapping(value = "/login")
    public Long login(@RequestBody MemberLoginForm memberLoginForm) {
        Long loginId = memberService.login(memberLoginForm.getEmail(), memberLoginForm.getPassword());
        return loginId;
    }
}
