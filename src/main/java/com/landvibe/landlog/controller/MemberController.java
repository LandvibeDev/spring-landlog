package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/members/new")
    public String createMemberForm() {
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String createMemberForm(MemberForm form) {
        Member member = new Member(form.getName(), form.getEmail(), form.getPassword());

        try {
            memberService.join(member);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return "redirect:/";

    }

    @GetMapping(value = "/members")
    public String memberList(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping(value = "/members/login")
    public String createLoginForm() {
        return "members/loginForm";
    }

    @PostMapping(value = "/members/login")
    public String login(LoginForm form, RedirectAttributes redirectAttributes) {
        Long redirectId;
        try {
            redirectId = memberService.logIn(form);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return "redirect:/";
        }

        redirectAttributes.addAttribute("creatorId", redirectId);
        return "redirect:/blogs";
    }
}
