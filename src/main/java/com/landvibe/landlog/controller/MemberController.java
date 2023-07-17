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
        Member member = new Member(form);
        try{
            memberService.join(member);
        }catch (IllegalStateException e){
            return "redirect:/members/new";
        }
        return "redirect:/";
    }

    @GetMapping(value = "")
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
        Member loginMember;
        try{
            loginMember=memberService.login(form);
        }catch (IllegalStateException e){
            return "redirect:/";
        }
        System.out.println("성공");
        return "redirect:/blogs?creatorId=" + loginMember.getId();
    }


}
