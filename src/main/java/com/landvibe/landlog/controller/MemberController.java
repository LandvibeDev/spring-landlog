package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.form.MemberJoinForm;
import com.landvibe.landlog.form.MemberLoginForm;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
    public String create(MemberJoinForm form) {
        Member member = new Member(form.getName(), form.getEmail(), form.getPassword());
        try {
            Long join = memberService.join(member);
            return "redirect:/";
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping()
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping(value = "/login")
    public String loginForm() {
        return "members/loginForm";
    }

    @PostMapping(value = "/login")
    public String login(MemberLoginForm memberLoginForm, RedirectAttributes redirect) {
        try {
            Long loginId = memberService.login(memberLoginForm.getEmail(), memberLoginForm.getPassword());
            redirect.addAttribute("creatorId", loginId);
            return "redirect:/blogs";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "redirect:/";
        }
    }
}