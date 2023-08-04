package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogForm;
import com.landvibe.landlog.service.BlogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/blogs")
public class BlogApiController {
    private final BlogService blogService;

    BlogApiController(BlogService blogService){
        this.blogService = blogService;
    }

    @GetMapping("")
    public List<Blog> list(@RequestParam long creatorId){
        blogService.validCreatorId(creatorId);

        return blogService.findBlogs(creatorId);
    }

    @PostMapping("")
    public Blog create(@RequestParam long creatorId, @RequestBody BlogForm blogForm){
        return blogService.register(creatorId, blogForm);
    }

    @GetMapping(value = "/{blogId}")
    public Blog getForUpdate(@RequestParam long creatorId, @PathVariable("blogId") long blogId){
        blogService.validCreatorId(creatorId);

        return blogService.findBlogById(blogId);
    }

    @PutMapping(value = "/{blogId}")
    public Blog update(@RequestParam long creatorId, @PathVariable("blogId") long blogId, @RequestBody BlogForm blogForm){
        blogService.validCreatorId(creatorId);

        return blogService.update(blogId, blogForm);
    }

    @DeleteMapping(value = "/{blogId}")
    public void delete(@RequestParam long creatorId, @PathVariable("blogId") Long blogId){
        blogService.validCreatorId(creatorId);

        blogService.delete(blogId);
    }
}
