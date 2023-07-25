package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.dto.BlogForm;
import com.landvibe.landlog.dto.BlogUpdateForm;
import com.landvibe.landlog.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.landvibe.landlog.ErrorMessage.NO_BLOG;
import static com.landvibe.landlog.ErrorMessage.NO_USER;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final MemberService memberService;

    public BlogService(BlogRepository blogRepository, MemberService memberService) {
        this.blogRepository = blogRepository;
        this.memberService = memberService;
    }

    public Blog registerBlog(Long creatorId, Blog blog) {
        validateCreatorId(creatorId);
        blogRepository.save(blog);
        return blog;
    }

    public List<Blog> findBlogsByCreatorId(Long creatorId) {
        validateCreatorId(creatorId);
        return blogRepository.findByCreatorId(creatorId);
    }

    public Blog findById(Long blogId, Long creatorId) {
        validateCreatorId(creatorId);
        return blogRepository.findByBlogId(blogId)
                .orElseThrow(() -> new IllegalArgumentException(NO_BLOG.message));
    }

    public void deleteBlog(Long creatorId, Long blogId) {
        validateCreatorId(creatorId);
        blogRepository.delete(blogId);
    }

    public Blog updateBlog(Long creatorId, Long id, Blog  blog) {
        validateCreatorId(creatorId);
        return blogRepository.update(id, blog);
    }

    public void validateCreatorId(Long creatorId) {
        memberService.isValidCreatorId(creatorId);
    }
}