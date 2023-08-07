package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.form.BlogForm;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/blogs")
public class BlogController {
    MemberService memberService;

    public BlogController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String blogList(@RequestParam Long creatorId, Model model) {
        Member member = memberService.findMemberById(creatorId);

        model.addAttribute("creatorId", creatorId);
        model.addAttribute("name", member.getName());

        return "blogs/blogList";
    }

    @GetMapping(value = "/new")
    public String createBlogForm(@RequestParam(name = "creatorId") Long creatorId, Model model) {
        Member member = memberService.findMemberById(creatorId);

        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", creatorId);

        return "blogs/createBlogForm";
    }

    @GetMapping(value = "/update")
    public String createUpdateForm(@RequestParam(name = "blogId") Long blogId, @RequestParam(name = "creatorId") Long creatorId,
                                   Model model) {
        Member member = memberService.findMemberById(creatorId);

        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", creatorId);
        model.addAttribute("blogId", blogId);

        return "blogs/updateBlogForm";
    }
}