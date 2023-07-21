package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Blog;

import com.landvibe.landlog.dto.BlogForm;
import com.landvibe.landlog.dto.BlogUpdateForm;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping("/blogs")
@Controller
public class BlogController {

    private final MemberService memberService;
    private final BlogService blogService;

    public BlogController(MemberService memberService, BlogService blogService) {
        this.memberService = memberService;
        this.blogService = blogService;
    }

    @GetMapping
    public String blog(@RequestParam Long creatorId, Model model) {
        List<Blog> blogs = blogService.findBlogsByCreatorId(creatorId);
        model.addAttribute("creatorId", creatorId);
        model.addAttribute("name", memberService.findById(creatorId).getName());
        model.addAttribute("blogs", blogs);
        return "blog/blogList";
    }

    @GetMapping("/new")
    public String createBlogForm(@RequestParam Long creatorId, Model model) {
        model.addAttribute("creatorId", creatorId);
        model.addAttribute("name", memberService.findById(creatorId).getName());
        return "blog/createBlogForm";
    }

    @PostMapping("/new")
    public String createBlog(@RequestParam Long creatorId, BlogForm blogForm, RedirectAttributes redirectAttributes) {
        blogService.registerBlog(creatorId, blogForm);
        redirectAttributes.addAttribute("creatorId", creatorId);
        return "redirect:/blogs";
    }

    @GetMapping("/update")
    public String updateBlogForm(@RequestParam Long creatorId, @RequestParam Long blogId, Model model) {
        Blog blog = blogService.findById(blogId);
        model.addAttribute("creatorId", creatorId);
        model.addAttribute("blog", blog);
        model.addAttribute("name", memberService.findById(creatorId).getName());
        return "blog/updateBlogForm";
    }

    @PostMapping("/update")
    public String updateBlog(@RequestParam Long creatorId, BlogUpdateForm blogUpdateForm, RedirectAttributes redirectAttributes) {
        blogService.updateBlog(blogUpdateForm, creatorId);
        redirectAttributes.addAttribute("creatorId", creatorId);
        return "redirect:/blogs";
    }

    @PostMapping("/delete")
    public String deleteBlog(@RequestParam Long blogId, @RequestParam Long creatorId, RedirectAttributes redirectAttributes) {
        blogService.deleteBlog(blogId, creatorId);
        redirectAttributes.addAttribute("creatorId", creatorId);
        return "redirect:/blogs";
    }
}
