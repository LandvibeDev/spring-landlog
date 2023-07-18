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
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(JoinForm form) {
        Member member = new Member();
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setEmail(form.getEmail());
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
    public String createLoginForm() {
        return "login/loginForm";
    }

    @PostMapping(value = "/members/login")
    public String login(LoginForm loginForm, RedirectAttributes redirectAttributes) throws Exception {
        try {
            Member member = memberService.login(loginForm);
            redirectAttributes.addAttribute("creatorId", member.getId());
        } catch (Exception e) {
            e.getMessage();
        }
        return "redirect:/blogs";
    }
}
