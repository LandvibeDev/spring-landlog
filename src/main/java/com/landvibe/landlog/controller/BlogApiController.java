package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.service.BlogService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/api/blogs")
public class BlogApiController {

    private final BlogService blogService;

    public BlogApiController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping()
    public List<Blog> list(@RequestParam long creatorId) {
        try {
            List<Blog> blogs = blogService.findBlogsByCreatorId(creatorId);
            return blogs;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @PostMapping()
    public Blog create(@RequestParam long creatorId, @RequestBody Blog blog) {
        try {
            Blog write = blogService.write(blog);
            return write;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @GetMapping(value = "/{blogId}")
    public Blog update(@PathVariable long blogId, @RequestParam long creatorId) {
        try {
            Blog blog = blogService.findById(blogId);
            return blog;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @PutMapping(value = "/{blogId}")
    public Blog update(@PathVariable long blogId, @RequestParam long creatorId, @RequestBody Blog blog) {
        try {
            Blog update = blogService.update(blog);
            return update;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @DeleteMapping(value = "/{blogId}")
    public void delete(@PathVariable long blogId, @RequestParam long creatorId) {
        try {
            blogService.deleteById(blogId);
        } catch (IllegalArgumentException e) {
            return;
        }
    }
}
