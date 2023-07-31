package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.form.LoginForm;
import com.landvibe.landlog.form.SignUpForm;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String createMemberForm() {
        return "members/createMemberForm";
    }

    @PostMapping(value = "/new")
    public String createMemberForm(SignUpForm form) {
        Member member = new Member(form.getName(), form.getEmail(), form.getPassword());

        try {
            memberService.join(member);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return "redirect:/";
    }

    @GetMapping(value = "")
    public String memberList(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping("/login")
    public String createLoginForm() {
        return "members/loginForm";
    }

    @PostMapping("/login")
    public String login(LoginForm form, RedirectAttributes redirectAttributes) {
        Long redirectId;

        try {
            redirectId = memberService.logIn(form);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "redirect:/";
        }

        redirectAttributes.addAttribute("creatorId", redirectId);
        return "redirect:/blogs";
    }
}
