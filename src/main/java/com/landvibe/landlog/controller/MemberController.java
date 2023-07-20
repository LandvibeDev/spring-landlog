package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        member.setEmain(form.getEmail());
        member.setPassword(form.getPassword());

        try {
            memberService.join(member);
        } catch (IllegalArgumentException e) {
            e.getMessage();
        } finally {
            return "redirect:/";
        }
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping(value = "/members/login")
    public String createLoginForm() {
        return "members/loginForm";
    }

    @PostMapping(value = "/members/login")
    public String login() {
        //성공시 블로그 페이지
        //실패시 홈 페이지
        return "redirect:/";
    }
}
