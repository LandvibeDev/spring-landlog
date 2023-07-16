package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
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
    public String create(MemberJoinForm form) {
        Member member = new Member();
        member.setName(form.getName());
        member.setEmail(form.getEmail());
        member.setPassword(form.getPassword());

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
    public String login(MemberLoginForm memberLoginForm, RedirectAttributes redirect){
        Optional<Member> loginResult = memberService.login(memberLoginForm.getEmail(), memberLoginForm.getPassword());

        // 로그인 실패
        if(loginResult.isEmpty()){
            return "redirect:/";
        }

        // 로그인 성공
        Long creatorId = loginResult.get().getId();
        redirect.addAttribute("creatorId", creatorId);
        return "redirect:/blogs";
    }
}
