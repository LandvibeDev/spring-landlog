package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.form.MemberLoginForm;
import com.landvibe.landlog.form.MemberNewForm;
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

    @GetMapping("/login")
    public String loginForm() {
        return "members/loginForm";
    }

    @PostMapping("/login")
    public String login(MemberLoginForm form, RedirectAttributes redirectAttributes) {
        try {
            Long memberId = memberService.login(form);
            redirectAttributes.addAttribute("creatorId", memberId);
        } catch (IllegalArgumentException e) {
            return "redirect:/";
        }
        return "redirect:/blogs";
    }

    @GetMapping("/new")
    public String createForm() {
        return "members/createMemberForm";
    }


    @PostMapping("/new")
    public String create(MemberNewForm form) {
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
}
