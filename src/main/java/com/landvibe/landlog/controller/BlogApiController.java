package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.service.BlogService;
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


    @GetMapping("{id}")
    public Blog blog(@RequestParam long creatorId, @PathVariable long id) {
        return blogService.findById(creatorId, id);
    }

    @PostMapping
    public Blog create(@RequestParam long creatorId, @RequestBody Blog blog) {
        return blogService.registerBlog(creatorId, blog);
    }

    @PutMapping("/{id}")
    public Blog update(@RequestParam long creatorId, @PathVariable long id, @RequestBody Blog blog) {
        return blogService.updateBlog(creatorId, id , blog);
    }


    @DeleteMapping("/{id}")
    public void delete(@RequestParam long creatorId, @PathVariable long id) {
        blogService.deleteBlog(creatorId, id);
    }

}