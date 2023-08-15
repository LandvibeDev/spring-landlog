package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.exception.BlogException;
import com.landvibe.landlog.exception.MemberException;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/blogs")
public class BlogController {

    private final MemberService memberService;
    private final BlogService blogService;

    public BlogController(MemberService memberService, BlogService blogService) {
        this.memberService = memberService;
        this.blogService = blogService;
    }

    @GetMapping()
    public String blogs(@RequestParam(value = "creatorId", required = false) Long creatorId, Model model)
            throws MemberException {
        Member member = memberService.findById(creatorId);

        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", creatorId);
        return "blogs/blogList";
    }

    @GetMapping(value = "/new")
    public String createForm(@RequestParam(value = "creatorId", required = false) Long creatorId, Model model)
            throws MemberException {
        Member member = memberService.findById(creatorId);

        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", creatorId);
        return "blogs/createBlogForm";
    }

    @GetMapping(value = "/update")
    public String updateForm(@RequestParam(value = "blogId", required = false) Long blogId,
                             @RequestParam(value = "creatorId", required = false) Long creatorId, Model model)
            throws MemberException, BlogException {
        Member member = memberService.findById(creatorId);
        Blog blog = blogService.findById(blogId);

        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", creatorId);
        model.addAttribute("blogId", blogId);
        return "blogs/updateBlogForm";
    }
}