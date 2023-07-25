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
@Controller()
public class BlogController {

    private final MemberService memberService;

    public BlogController(MemberService memberService, BlogService blogService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String blog(@RequestParam Long creatorId, Model model) {
        model.addAttribute("creatorId", creatorId);
        model.addAttribute("name", memberService.findById(creatorId).getName());
        return "blog/blogList";
    }

    @GetMapping("/new")
    public String createBlogForm(@RequestParam Long creatorId, Model model) {
        model.addAttribute("creatorId", creatorId);
        model.addAttribute("name", memberService.findById(creatorId).getName());
        return "blog/createBlogForm";
    }

    @PostMapping("/new")
    public String createBlog(@RequestParam Long creatorId, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("creatorId", creatorId);
        return "redirect:/blogs";
    }

    @GetMapping("/update")
    public String updateBlogForm(@RequestParam Long creatorId, Model model) {
        model.addAttribute("creatorId", creatorId);
        model.addAttribute("name", memberService.findById(creatorId).getName());
        return "blog/updateBlogForm";
    }

    @PostMapping("/update")
    public String updateBlog(@RequestParam Long creatorId, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("creatorId", creatorId);
        return "redirect:/blogs";
    }

    @PostMapping("/delete")
    public String deleteBlog(@RequestParam Long blogId, @RequestParam Long creatorId, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("creatorId", creatorId);
        return "redirect:/blogs";
    }
}