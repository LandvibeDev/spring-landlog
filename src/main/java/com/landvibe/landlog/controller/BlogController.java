package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.form.BlogForm;
import com.landvibe.landlog.form.BlogUpdateForm;
import com.landvibe.landlog.form.MemberNewForm;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", member.getId());
        model.addAttribute("blogs", blogService.findBlogsByCreatorId(creatorId));
        return "blog/blogList";
    }

    @GetMapping("/new")
    public String createForm(@RequestParam Long creatorId, Model model) {
        Member member = memberService.findOne(creatorId);
        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", member.getId());
        return "blog/createBlogForm";
    }

    @PostMapping("/new")
    public String create(@RequestParam Long creatorId, BlogForm form, RedirectAttributes redirectAttributes) {
        blogService.create(creatorId, form);
        redirectAttributes.addAttribute("creatorId", creatorId);
        return "redirect:/blogs";
    }

    @GetMapping("/update")
    public String updateForm(@RequestParam Long creatorId, @RequestParam Long blogId, Model model) {
        Member member = memberService.findOne(creatorId);
        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", member.getId());
        model.addAttribute("blog", blogService.findBlogByBlogId(blogId));
        return "blog/updateBlogForm";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long creatorId, BlogUpdateForm form, RedirectAttributes redirectAttributes) {
        blogService.update(creatorId, form);
        redirectAttributes.addAttribute("creatorId", creatorId);
        return "redirect:/blogs";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long blogId, @RequestParam Long creatorId, RedirectAttributes redirectAttributes) {
        blogService.delete(creatorId, blogId);
        redirectAttributes.addAttribute("creatorId", creatorId);
        return "redirect:/blogs";
    }
}
