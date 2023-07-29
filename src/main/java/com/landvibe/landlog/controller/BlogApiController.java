package com.landvibe.landlog.controller;


import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogCreateForm;
import com.landvibe.landlog.form.BlogUpdateForm;
import com.landvibe.landlog.service.BlogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/blogs")
public class BlogApiController {

    private final BlogService blogService;

    BlogApiController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public List<Blog> list(@RequestParam Long creatorId) {
        return blogService.findAllBlogs(creatorId);
    }

    @GetMapping("/{blogId}")
    public Blog getByBlogId(@PathVariable Long blogId, @RequestParam("creatorId") Long creatorId) {
        return blogService.findBlogById(blogId);
    }

    @PostMapping
    public Blog create(@RequestParam long creatorId, @RequestBody Blog blog) {
        BlogCreateForm form = new BlogCreateForm(blog.getTitle(), blog.getContents());
        blogService.createBlog(creatorId, form);
        return blog;
    }

    @PutMapping("/{id}")
    public Blog update(@RequestParam long creatorId, @PathVariable long id, @RequestBody Blog blog) {
        BlogUpdateForm form = new BlogUpdateForm(blog.getTitle(), blog.getContents());
        blogService.update(id, form);
        return blog;
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestParam long creatorId, @PathVariable long id) {
        blogService.delete(id);
    }

}
