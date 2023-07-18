package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/blogs")
public class BlogController {

    private final MemberService memberService;

    public BlogController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping()
    public String blogForm(@RequestParam("creatorId") Long id, Model model) {

        try {
            Member member = memberService.findOne(id);
            model.addAttribute("name", member.getName());
            return "/blogs/blogList";
        } catch (IllegalStateException e) {
            return "redirect:/";
        }

    }
}
