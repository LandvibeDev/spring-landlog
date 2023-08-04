package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogForm;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/blogs")
public class BlogApiController {
    private final BlogService blogService;
    private final MemberService memberService;

    BlogApiController(BlogService blogService, MemberService memberService){
        this.blogService = blogService;
        this.memberService = memberService;
    }

    @GetMapping("")
    public List<Blog> list(@RequestParam long creatorId){
        blogService.validCreatorId(creatorId);

        return blogService.findBlogs(creatorId);
    }

    @PostMapping("")
    public Blog create(@RequestParam long creatorId, @RequestBody Blog blog){
        BlogForm createBlogForm = new BlogForm(blog.getTitle(), blog.getContents());
        return blogService.register(creatorId, createBlogForm);
    }

    @GetMapping(value = "/{blogId}")
    public Blog get(@RequestParam long creatorId, @PathVariable("blogId") Long blogId){
        blogService.validCreatorId(creatorId);
        return blogService.findBlogById(blogId);
    }

    @PutMapping(value = "/{blogId}")
    public Blog update(@RequestParam long creatorId, @PathVariable("blogId") Long blogId, @RequestBody Blog blog){
        BlogForm updateBlogForm = new BlogForm(blog.getTitle(), blog.getContents());
        return blogService.update(blogId, updateBlogForm);
    }

    @DeleteMapping(value = "/{blogId}")
    public void delete(@RequestParam long creatorId, @PathVariable("blogId") Long blogId){
        blogService.validCreatorId(creatorId);
        blogService.delete(blogId);
    }
}
