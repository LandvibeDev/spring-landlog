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
        List<Blog> blogs = blogService.findBlogsByCreatorId(creatorId);
        return blogs;
    }

    @PostMapping()
    public Blog create(@RequestParam long creatorId, @RequestBody Blog blog) {
        Blog write = blogService.write(blog);
        return write;
    }

    @GetMapping(value = "/{blogId}")
    public Blog update(@PathVariable long blogId, @RequestParam long creatorId) {
        Blog blog = blogService.findById(blogId);
        return blog;
    }

    @PutMapping(value = "/{blogId}")
    public Blog update(@PathVariable long blogId, @RequestParam long creatorId, @RequestBody Blog blog) {
        Blog update = blogService.update(blog);
        return update;
    }

    @DeleteMapping(value = "/{blogId}")
    public void delete(@PathVariable long blogId, @RequestParam long creatorId) {
        blogService.deleteById(blogId);
    }
}
