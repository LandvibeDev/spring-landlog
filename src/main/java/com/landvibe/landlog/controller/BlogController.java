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
    //
    private final MemberService memberService;
    private final BlogService blogService;

    public BlogController(MemberService memberService, BlogService blogService) {
        this.memberService = memberService;
        this.blogService = blogService;
    }

    @GetMapping()
    public String blogs(@RequestParam(value = "creatorId", required = false) Long creatorId, Model model) {
        try {
            List<Blog> blogs = blogService.findBlogsByCreatorId(creatorId);
            Member member = memberService.findById(creatorId);

            model.addAttribute("blogs", blogs);
            model.addAttribute("name", member.getName());
            model.addAttribute("creatorId", creatorId);
            return "blogs/blogList";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping(value = "/new")
    public String createForm(@RequestParam(value = "creatorId", required = false) Long creatorId, Model model) {
        try {
            Member member = memberService.findById(creatorId);

            model.addAttribute("name", member.getName());
            model.addAttribute("creatorId", creatorId);
            return "blogs/createBlogForm";

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping(value = "/new")
    public String create(@RequestParam(value = "creatorId", required = false) Long creatorId,
                         BlogForm form, RedirectAttributes redirect) {
        try {
            Blog blog = new Blog(creatorId, form.getTitle(), form.getContents());
            blogService.write(blog);

            redirect.addAttribute("creatorId", creatorId);
            return "redirect:/blogs";

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping(value = "/update")
    public String updateForm(@RequestParam(value = "blogId", required = false) Long blogId,
                             @RequestParam(value = "creatorId", required = false) Long creatorId, Model model) {
        try {
            Member member = memberService.findById(creatorId);
            Blog blog = blogService.findById(blogId);

            model.addAttribute("name", member.getName());
            model.addAttribute("creatorId", creatorId);
            model.addAttribute("blog", blog);
            return "blogs/updateBlogForm";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "redirect:/";
        }
    }

    @PatchMapping(value = "/update")
    public String update(@RequestParam(value = "id", required = false) Long blogId,
                         @RequestParam(value = "creatorId", required = false) Long creatorId,
                         BlogForm form, RedirectAttributes redirect) {
        try {
            Blog blog = new Blog(blogId, creatorId, form.getTitle(), form.getContents());
            blogService.update(blog);

            redirect.addAttribute("creatorId", creatorId);
            return "redirect:/blogs";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "redirect:/";
        }
    }

    @DeleteMapping(value = "/delete")
    public String delete(@RequestParam(value = "blogId", required = false) Long blogId,
                         @RequestParam(value = "creatorId", required = false) Long creatorId,
                         RedirectAttributes redirect) {
        try {
            blogService.deleteById(blogId);

            redirect.addAttribute("creatorId", creatorId);
            return "redirect:/blogs";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "redirect:/";
        }
    }
}
