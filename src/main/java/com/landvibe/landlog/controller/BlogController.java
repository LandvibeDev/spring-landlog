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
    BlogService blogService;

    public BlogController(MemberService memberService, BlogService blogService) {
        this.memberService = memberService;
        this.blogService = blogService;
    }

    @GetMapping(value = "")
    public String blogList(@RequestParam Long creatorId, Model model) {
        Member member = memberService.findMemberById(creatorId);
        List<Blog> blogs = blogService.findBlogs(creatorId);

        model.addAttribute("creatorId", creatorId);
        model.addAttribute("name", member.getName());
        model.addAttribute("blogs", blogs);

        return "blogs/blogList";
    }

    @GetMapping(value = "/new")
    public String createBlogForm(@RequestParam(name = "creatorId") Long creatorId, Model model) {
        Member member = memberService.findMemberById(creatorId);

        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", creatorId);

        return "blogs/createBlogForm";
    }

    @PostMapping(value = "/new")
    public String registerBlog(@RequestParam(name = "creatorId") Long creatorId, BlogForm form,
                               RedirectAttributes redirect) {
        Blog blog = new Blog(creatorId, form.getTitle(), form.getContents());

        blogService.register(creatorId, blog);

        redirect.addAttribute("creatorId", creatorId);

        return "redirect:/blogs";
    }

    @GetMapping(value = "/update")
    public String createUpdateForm(@RequestParam(name = "blogId") Long blogId, @RequestParam(name = "creatorId") Long creatorId,
                                   Model model) {
        Member member = memberService.findMemberById(creatorId);
        Blog blog = blogService.findBlogByBlogIdAndCreatorId(creatorId, blogId);

        model.addAttribute("name", member.getName());
        model.addAttribute("creatorId", creatorId);
        model.addAttribute("blog", blog);

        return "blogs/updateBlogForm";
    }

    @PostMapping(value = "/update")
    public String updateBlog(@RequestParam(name = "creatorId") Long creatorId, @RequestParam(name = "id") Long blogId,
                             BlogForm form, RedirectAttributes redirect) {
        blogService.updateBlog(creatorId, blogId, form);

        redirect.addAttribute("creatorId", creatorId);

        return "redirect:/blogs";
    }

    @PostMapping(value = "/delete")
    public String deleteBlog(@RequestParam(name = "blogId") Long blogId, @RequestParam(name = "creatorId") Long creatorId,
                             RedirectAttributes redirect){
        blogService.deleteBlog(creatorId, blogId);

        redirect.addAttribute("creatorId", creatorId);

        return "redirect:/blogs";
    }

}
