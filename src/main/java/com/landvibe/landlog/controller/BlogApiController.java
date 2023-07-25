package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/api/blogs")
@RestController
public class BlogApiController {

    private final BlogService blogService;

    public BlogApiController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public List<Blog> list(@RequestParam Long creatorId) {
        return blogService.findBlogsByCreatorId(creatorId);
    }


    @GetMapping("{blogId}")
    public Blog blog(@RequestParam long creatorId, @PathVariable long blogId) {
        System.out.println("!!!!!!!!!!");
        Blog blog = blogService.findById(blogId, creatorId);
        return blog;
    }

    @PostMapping
    public Blog create(@RequestParam long creatorId, @RequestBody Blog blog) {
        return blogService.registerBlog(creatorId, blog);
    }

    @PutMapping("/update/{id}")
    public Blog update(@RequestParam long creatorId, @PathVariable long id, @RequestBody Blog blog) {
        return blogService.updateBlog(creatorId, id , blog);
    }


    @DeleteMapping("delete/{id}")
    public void delete(@RequestParam long creatorId, @PathVariable long id) {
        blogService.deleteBlog(creatorId, id);
    }

}