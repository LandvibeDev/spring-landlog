package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/blogs")
public class BlogController {
    private final MemberService memberService;
    private final BlogService blogService;

    public BlogController(MemberService memberService, BlogService blogService) {
        this.memberService = memberService;
        this.blogService = blogService;
    }

    @GetMapping
    public String blog(@RequestParam Long creatorId, Model model) {
        Member member = memberService.findOne(creatorId);
        List<Blog> blogs = blogService.findBlogsByCreatorId(creatorId);
        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", member.getId());
        model.addAttribute("blogs", blogs);
        return "blog/blogList";
    }
}
