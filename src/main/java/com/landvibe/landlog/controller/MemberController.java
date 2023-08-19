package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.dto.JoinForm;
import com.landvibe.landlog.dto.LoginForm;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping("/members")
@Controller
public class MemberController {

    @GetMapping(value = "/new")
    public String createMemberForm() {
        return "members/createMemberForm";
    }

    @PostMapping(value = "/new")
    public String create() {
        return "redirect:/";
    }

    @GetMapping
    public String list() {
        return "members/memberList";
    }

    @GetMapping(value = "/login")
    public String createLoginForm() {
        return "login/loginForm";
    }

}

