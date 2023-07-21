package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.form.LoginForm;
import com.landvibe.landlog.form.MemberForm;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping(value = "/new")
    public String create(MemberForm form) {
        String email = form.getEmail();
        String password = form.getPassword();
        String name = form.getPassword();
        Member member = new Member(name, email, password);
        try {
            memberService.join(member);
            return "redirect:/";
        } catch (IllegalStateException e) {
            return "redirect:/members/new";
        }
    }

    @GetMapping()
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "members/loginForm";
    }

    @PostMapping("/login")
    public String login(LoginForm form) {

        try {
            Member loginMember = memberService.login(form);
            return "redirect:/blogs?creatorId=" + loginMember.getId();
        } catch (IllegalStateException e) {
            return "redirect:/";
        }

    }


}