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
        Member member = new Member();
        member.setName(form.getName());
        member.setEmail(form.getEmail());
        member.setPassword(form.getPassword());
        try{
            memberService.join(member);
        }catch (IllegalStateException e){
            return "redirect:/members/new";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping("/members/login")
    public String loginForm() {
        return "members/loginForm";
    }

    @PostMapping("/members/login")
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

    @GetMapping("/blogs")
    public String blogForm(@RequestParam("creatorId") Long id, Model model) {
        ;
        Optional<Member> optionalMember = memberService.findOne(id);
        Member member = optionalMember.get();
        System.out.println(member.getName());
        model.addAttribute("name", member.getName());
        return "/blogs/blogList";
    }
}
