package com.landvibe.landlog.controller;

import com.landvibe.landlog.controller.form.BlogForm;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api")
public class BlogApiController {

    private final MemberService memberService;
    private final BlogService blogService;

    public BlogApiController(MemberService memberService, BlogService blogService) {
        this.memberService = memberService;
        this.blogService = blogService;
    }

    @GetMapping(value = "/blogs")
    public List<Blog> list(@RequestParam Long creatorId) {
        return blogService.findBlogsByCreatorId(creatorId);
    }

    @PostMapping(value = "/blogs")
    public Blog create(@RequestParam Long creatorId, @RequestBody BlogForm blogForm) {
        memberService.findMemberById(creatorId);
        return blogService.createBlog(creatorId, blogForm.getTitle(), blogForm.getContents());
    }

    @GetMapping(value = "/blogs/{blogId}")
    public Blog getBlogWhenUpdate(@RequestParam Long creatorId, @PathVariable("blogId") Long blogId) {
        return blogService.findBlogByBlogIdAndCreatorId(creatorId, blogId);
    }

    @PutMapping(value = "/blogs/{blogId}")
    public Blog update(@RequestParam Long creatorId, @PathVariable("blogId") Long blogId, @RequestBody BlogForm blogForm) {
        return blogService.updateBlog(creatorId, blogId, blogForm.getTitle(), blogForm.getContents());
    }

    @DeleteMapping(value = "/blogs/{blogId}")
    public void delete(@RequestParam Long creatorId, @PathVariable("blogId") Long blogId) {
        blogService.deleteBlog(creatorId, blogId);
    }
}