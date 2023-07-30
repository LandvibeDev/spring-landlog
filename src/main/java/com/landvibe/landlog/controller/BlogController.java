package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.controller.form.BlogForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BlogController {

    private final MemberService memberService;
    private final BlogService blogService;

    public BlogController(MemberService memberService, BlogService blogService) {
        this.memberService = memberService;
        this.blogService = blogService;
    }

    // <--GET-->

    @GetMapping("/blogs")
    public String list(@RequestParam Long creatorId, Model model) {
        Member member = memberService.findMemberById(creatorId); // 예외 발생 가능
        List<Blog> blogs = blogService.findBlogsByCreatorId(creatorId); // 예외 발생 가능

        model.addAttribute("name", member.getName());
        model.addAttribute("blogs", blogs);
        model.addAttribute("creatorId", member.getId());
        return "blogs/blogList";
    }

    @GetMapping("/blogs/new")
    public String showCreateBlogForm(@RequestParam Long creatorId, Model model) {
        Member member = memberService.findMemberById(creatorId); // 예외 발생 가능

        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", member.getId());
        return "blogs/createBlogForm";
    }

    @GetMapping("/blogs/update")
    public String showUpdateBlogForm(@RequestParam Long creatorId, @RequestParam Long blogId, Model model) {
        Member member = memberService.findMemberById(creatorId); // 예외
        Blog blog = blogService.findBlogByBlogIdAndCreatorId(creatorId, blogId); // 예외

        model.addAttribute("blog", blog);
        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", creatorId);
        return "blogs/updateBlogForm";
    }

    // <--POST-->

    @PostMapping("/blogs/new")
    public String createBlog(@RequestParam Long creatorId, BlogForm blogForm, RedirectAttributes redirectAttributes) {
        blogService.createBlog(creatorId, blogForm.getTitle(), blogForm.getContents()); // 예외

        redirectAttributes.addAttribute("creatorId", creatorId);
        return "redirect:/blogs";
    }

    @PostMapping("/blogs/update") // FetchMapping ?
    public String updateBlog(@RequestParam Long creatorId, @RequestParam Long blogId,
                             BlogForm blogForm, RedirectAttributes redirectAttributes) {
        blogService.updateBlog(creatorId, blogId, blogForm.getTitle(), blogForm.getContents()); // 예외

        redirectAttributes.addAttribute("creatorId", creatorId);
        return "redirect:/blogs";
    }

    @PostMapping("/blogs/delete") // DeleteMapping ?
    public String deleteBlog(@RequestParam Long creatorId, @RequestParam Long blogId, RedirectAttributes redirectAttributes) {
        blogService.deleteBlog(creatorId, blogId); // 예외

        redirectAttributes.addAttribute("creatorId", creatorId);
        return "redirect:/blogs";
    }

}
