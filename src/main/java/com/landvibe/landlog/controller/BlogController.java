package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.form.BlogCreateForm;
import com.landvibe.landlog.form.BlogUpdateForm;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    public void validateCreatorId(Long creatorId) throws Exception {
        if (creatorId == null) {
            throw new Exception();
        }
    }

    public void validateCreatorIdAndBlogId(Long creatorId, Long blogId) throws Exception {
        if (creatorId == null || blogId == null) {
            throw new Exception();
        }
    }

    @GetMapping()
    public String blogForm(@RequestParam("creatorId") @Nullable Long creatorId, Model model) {
        try {
            validateCreatorId(creatorId);
            Member member = memberService.findById(creatorId);
            List<Blog> blogList = blogService.findAllBlogs(creatorId);
            model.addAttribute("name", member.getName());
            System.out.println("이름" + member.getName());
            model.addAttribute("creatorId", creatorId);
            model.addAttribute("blogs", blogList);
            return "/blogs/blogList";
        } catch (Exception e) {
            System.out.println("creatorId가 존재하지 않습니다.");
            return "redirect:/";
        }

    }

    @GetMapping("/new")
    public String createBlogForm(@RequestParam("creatorId") @Nullable Long creatorId, Model model) {
        try {
            validateCreatorId(creatorId);
            Member member = memberService.findById(creatorId);
            model.addAttribute("name", member.getName());
            model.addAttribute("creatorId", creatorId);
            return "blogs/createBlogForm";
        } catch (Exception e) {
            System.out.println("creatorId가 존재하지 않습니다.");
            return "redirect:/";
        }
    }

    @PostMapping("/new")
    public String createBlog(BlogCreateForm form, @RequestParam("creatorId") @Nullable Long creatorId) {
        String title = form.getTitle();
        String content = form.getContents();
        System.out.println(form.getContents());
        Blog blog = new Blog(title, creatorId, content);
        blogService.createBlog(creatorId, form);
        return "redirect:/blogs?creatorId=" + creatorId;
    }

    @GetMapping("/update")
    public String updateBlogForm(@RequestParam("blogId") Long blogId, @RequestParam("creatorId") @Nullable Long creatorId, Model model) {
        Member member = memberService.findById(creatorId);
        Blog blog = blogService.findBlogById(blogId);
        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", creatorId);
        model.addAttribute("blog", blog);
        return "blogs/updateBlogForm";
    }

    @PostMapping("/update")
    public String updateBlog(@RequestParam("creatorId") @Nullable Long creatorId, @RequestParam("id") @Nullable Long id, BlogUpdateForm form) {
        try {
            System.out.println(form.getContents());
            validateCreatorIdAndBlogId(creatorId, id);
            blogService.update(id, form);
            return "redirect:/blogs?creatorId=" + creatorId;
        } catch (Exception e) {
            System.out.println("creatorId나 blogId가 존재하지 않습니다.");
            return "redirect:/";
        }
    }

    @PostMapping("/delete")
    public String deleteBlog(@RequestParam("blogId") @Nullable Long blogId, @RequestParam("creatorId") @Nullable Long creatorId) {
        try {
            validateCreatorId(creatorId);
            blogService.delete(blogId);
            return "redirect:/blogs?creatorId=" + creatorId;
        } catch (Exception e) {
            System.out.println("creatorId가 존재하지 않습니다.");
            return "redirect:/";
        }
    }
}


