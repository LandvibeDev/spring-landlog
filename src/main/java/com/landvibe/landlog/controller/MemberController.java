package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.form.MemberForm;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(MemberForm form) {
        Member member = Member.builder()
                        .name(form.getName())
                                .email(form.getEmail())
                                        .password(form.getPassword())
                                                .build();
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping(value = "/members/login")
    public String loginForm() {
        return "members/loginForm";
    }

    @PostMapping(value = "/members/login")
    public String login(@ModelAttribute LoginMemberForm form, RedirectAttributes redirectAttributes) {
        Optional<Member> member = memberService.login(form.getEmail(), form.getPassword());
        if (!member.isEmpty()) {
            redirectAttributes.addAttribute("creatorId", member.get().getId());
            return "redirect:/blogs";
        }
        return "redirect:/";
    }

}
