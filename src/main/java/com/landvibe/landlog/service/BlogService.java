package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.exception.BlogException;
import com.landvibe.landlog.repository.BlogRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.landvibe.landlog.exception.BaseException.NO_BLOG;


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

    public Blog findById(Long creatorId, Long id) {
        validateCreatorId(creatorId);
        return blogRepository.findById(id)
                .orElseThrow(() -> new BlogException(NO_BLOG));
    }

    public void deleteBlog(Long creatorId, Long id) {
        validateCreatorId(creatorId);
        blogRepository.delete(id);
    }

    public Blog updateBlog(Long creatorId, Long id, Blog blog) {
        validateCreatorId(creatorId);
        isValidBlogId(id);
        return blogRepository.update(id, blog);
    }

    public void validateCreatorId(Long creatorId) {
        memberService.isValidCreatorId(creatorId);
    }

    public void isValidBlogId(Long id) {
        blogRepository.findById(id)
                .orElseThrow(() -> new BlogException(NO_BLOG));
    }
}