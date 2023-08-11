package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.form.BlogCreateForm;
import com.landvibe.landlog.form.BlogUpdateForm;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


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
    public String blogForm(@RequestParam("creatorId") Long creatorId, Model model) {
        Member member = memberService.findById(creatorId);
        List<Blog> blogList = blogService.findAllBlogs(creatorId);
        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", creatorId);
        model.addAttribute("blogs", blogList);
        return "/blogs/blogList";
    }

    @GetMapping("/new")
    public String createBlogForm(@RequestParam("creatorId") Long creatorId, Model model) {
        Member member = memberService.findById(creatorId);
        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", creatorId);
        return "blogs/createBlogForm";
    }

    @PostMapping("/new")
    public String createBlog(BlogCreateForm form, @RequestParam("creatorId") Long creatorId) {
        blogService.createBlog(creatorId, form);
        return "redirect:/blogs?creatorId=" + creatorId;
    }

    @GetMapping("/update")
    public String updateBlogForm(@RequestParam("blogId") Long blogId, @RequestParam("creatorId") Long creatorId, Model model) {
        Member member = memberService.findById(creatorId);
        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", creatorId);
        model.addAttribute("blogId", blogId);
        return "blogs/updateBlogForm";
    }


    @PatchMapping("/update")
    public String updateBlog(@RequestParam("creatorId") Long creatorId, @RequestParam("id") Long id, BlogUpdateForm form) {
        blogService.update(id, form);
        return "redirect:/blogs?creatorId=" + creatorId;
    }


    @DeleteMapping("/delete")
    public String deleteBlog(@RequestParam("blogId") Long blogId, @RequestParam("creatorId") Long creatorId) {
        blogService.delete(blogId);
        return "redirect:/blogs?creatorId=" + creatorId;
    }
}

