package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BlogController {

    private final MemberService memberService;

    public BlogController(MemberService memberService) {
        this.memberService = memberService;
    }

    // <--GET-->

    @GetMapping("/blogs")
    public String showBlogs(@RequestParam Long creatorId, Model model) {
        Member member = memberService.findMemberById(creatorId); // 예외

        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", member.getId());
        return "blogs/blogList";
    }

    @GetMapping("/blogs/new")
    public String showCreateBlogForm(@RequestParam Long creatorId, Model model) {
        Member member = memberService.findMemberById(creatorId); // 예외

        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", member.getId());
        return "blogs/createBlogForm";
    }

    @GetMapping("/blogs/update")
    public String showUpdateBlogForm(@RequestParam Long creatorId, @RequestParam Long blogId, Model model) {
        Member member = memberService.findMemberById(creatorId); // 예외

        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", creatorId);
        model.addAttribute("blogId", blogId);
        return "blogs/updateBlogForm";
    }

}
