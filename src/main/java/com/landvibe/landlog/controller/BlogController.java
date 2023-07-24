package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;

import static com.landvibe.landlog.exceptions.ExceptionHandling.exceptionHandlingMethod;

@Slf4j
@Controller
public class BlogController {
    private final MemberService memberService;
    private final BlogService blogService;

    public BlogController(MemberService memberService, BlogService blogService) {
        this.memberService = memberService;
        this.blogService = blogService;
    }

    @GetMapping("/blogs")
    public String blog(@RequestParam Long creatorId, Model model) {
        exceptionHandlingMethod(creatorId, memberService);
        Optional<Member> member = memberService.findOne(creatorId);
        if (member.isEmpty()) {
            return "home";
        }
        List<Blog> blogs = blogService.findBlogs(creatorId);
        model.addAttribute("blogs", blogs);
        model.addAttribute("creatorId", creatorId);
        model.addAttribute("name", member.get().getName());
        return "blogList";
    }


    @GetMapping("/blogs/new")
    public String createBlogForm(@RequestParam Long creatorId, Model model) {
        exceptionHandlingMethod(creatorId, memberService);
        Optional<Member> member = memberService.findOne(creatorId);
        Member blogMember = new Member();
        if (!member.isEmpty()) {
            blogMember = member.get();
        }
        model.addAttribute("name", blogMember.getName());
        model.addAttribute("creatorId", creatorId);
        return "createBlogForm";
    }

    @PostMapping("/blogs/new")
    public String createBlog(@RequestParam String title,
                             @RequestParam String contents,
                             @RequestParam Long creatorId,
                             RedirectAttributes redirectAttributes
    ) {
        exceptionHandlingMethod(creatorId, memberService);
        Blog blog = new Blog(creatorId, title, contents);
        Optional<Member> member = memberService.findOne(creatorId);
        if (!member.isEmpty()) {
            redirectAttributes.addAttribute("creatorId", creatorId);
        }
        blogService.create(blog);
        return "redirect:/blogs";
    }

    @GetMapping("/blogs/update")
    public String updateForm(@RequestParam Long blogId,
                             @RequestParam Long creatorId,
                             Model model) {
        exceptionHandlingMethod(creatorId, memberService);
        Blog blog = blogService.findOne(blogId, creatorId);
        Optional<Member> member = memberService.findOne(creatorId);
        if (!member.isEmpty()) {
            model.addAttribute("name", member.get().getName());
        }
        model.addAttribute("blog", blog);
        model.addAttribute("creatorId", creatorId);
        return "updateBlogForm";
    }

    @PutMapping("/blogs/update")
    public String update(@RequestParam Long id,
                         @RequestParam(required = false) Long creatorId,
                         @ModelAttribute UpdateBlogForm updateBlogForm,
                         RedirectAttributes redirectAttributes) {
        exceptionHandlingMethod(creatorId, memberService);
        blogService.update(id, creatorId, updateBlogForm);
        redirectAttributes.addAttribute("creatorId", creatorId);
        return "redirect:/blogs";
    }

    @PostMapping("/blogs/delete")
    public String delete(@RequestParam Long blogId,
                         @RequestParam Long creatorId,
                         RedirectAttributes redirectAttributes) {
        exceptionHandlingMethod(creatorId, memberService);
        blogService.delete(blogId, creatorId);
        redirectAttributes.addAttribute("creatorId", creatorId);
        return "redirect:/blogs";
    }
  
  
}
