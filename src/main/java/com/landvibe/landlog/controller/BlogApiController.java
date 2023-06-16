package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.error.LandLogException;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.landvibe.landlog.error.ErrorCode.NOT_FOUND_BLOG;
import static com.landvibe.landlog.error.ErrorCode.UNAUTHORIZED_USER;

@RequestMapping(value = "/v1/api")
@RestController
public class BlogApiController {

    private final BlogService blogService;
    private final MemberService memberService;

    public BlogApiController(BlogService blogService, MemberService memberService) {
        this.blogService = blogService;
        this.memberService = memberService;
    }

    @GetMapping(value = "/blogs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Blog> list(@RequestParam long creatorId) {
        memberService.findOne(creatorId).orElseThrow(() -> new LandLogException(UNAUTHORIZED_USER)); // 인증
        return blogService.findBlogsByCreator(creatorId);
    }

    @GetMapping(value = "/blogs/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Blog get(@RequestParam long creatorId, @PathVariable long id) {
        memberService.findOne(creatorId).orElseThrow(() -> new LandLogException(UNAUTHORIZED_USER)); // 인증
        return blogService.findBlog(id).orElseThrow(() -> new LandLogException(NOT_FOUND_BLOG)); // 없으면 에러
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(value = "/blogs", produces = MediaType.APPLICATION_JSON_VALUE)
    public Blog create(@RequestParam long creatorId, @RequestBody Blog blog) {
        memberService.findOne(creatorId).orElseThrow(() -> new LandLogException(UNAUTHORIZED_USER)); // 인증
        blog.setCreatorId(creatorId);
        return blogService.createBlog(blog);
    }

    @PutMapping(value = "/blogs/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Blog update(@RequestParam long creatorId, @PathVariable long id, @RequestBody Blog blog) {
        memberService.findOne(creatorId).orElseThrow(() -> new LandLogException(UNAUTHORIZED_USER)); // 인증
        blogService.findBlog(id).orElseThrow(() -> new LandLogException(NOT_FOUND_BLOG)); // 없으면 에러
        return blogService.updateBlog(blog);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/blogs/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@RequestParam long creatorId, @PathVariable long id) {
        memberService.findOne(creatorId).orElseThrow(() -> new LandLogException(UNAUTHORIZED_USER)); // 인증
        blogService.findBlog(id).orElseThrow(() -> new LandLogException(NOT_FOUND_BLOG)); // 없으면 에러
        blogService.deleteBlog(id);
    }

}
