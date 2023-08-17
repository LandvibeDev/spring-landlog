package com.landvibe.landlog.controller;

import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @GetMapping()
    public String list() {
        return "members/memberList";
    }

    @GetMapping(value = "/login")
    public String loginForm() {
        return "members/loginForm";
    }
}