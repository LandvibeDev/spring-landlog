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
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping(value = "/new")
    public String create(JoinForm form) {
        Member member = new Member(form.getName(), form.getEmail(), form.getPassword());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping(value = "/login")
    public String createLoginForm() {
        return "login/loginForm";
    }

    @PostMapping(value = "/login")
    public String login(LoginForm loginForm, RedirectAttributes redirectAttributes) {
        try {
            Long memberId = memberService.login(loginForm);
            redirectAttributes.addAttribute("creatorId", memberId);
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
        return "redirect:/blogs";
    }
}
