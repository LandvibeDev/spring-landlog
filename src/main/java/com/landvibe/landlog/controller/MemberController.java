package com.landvibe.landlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
public class MemberController {

    @GetMapping(value = "/new")
    public String createMemberForm() {
        return "members/createMemberForm";
    }

    @GetMapping
    public String memberList() {
        return "members/memberList";
    }

    @GetMapping(value = "/login")
    public String createLoginForm() {
        return "members/loginForm";
    }
}
