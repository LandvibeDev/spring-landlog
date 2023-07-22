package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.dto.BlogForm;
import com.landvibe.landlog.dto.BlogUpdateForm;
import com.landvibe.landlog.repository.BlogRepository;
import org.springframework.stereotype.Service;

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

    public void registerBlog(Long creatorId, BlogForm blogForm) {
        validateCreatorId(creatorId);
        Blog blog = new Blog(creatorId, blogForm.getTitle(), blogForm.getContent());
        blogRepository.save(blog);
    }

    public List<Blog> findBlogsByCreatorId(Long creatorId) {
        validateCreatorId(creatorId);
        return blogRepository.findByCreatorId(creatorId);
    }

    public Blog findById(Long blogId) {
        return blogRepository.findByBlogId(blogId)
                .orElseThrow(() -> new IllegalArgumentException(NO_BLOG.message));
    }

    public void deleteBlog(Long blogId, Long creatorId) {
        validateCreatorId(creatorId);
        blogRepository.delete(blogId);
    }

    public Blog updateBlog(BlogUpdateForm blogUpdateForm, Long creatorId) {
        validateCreatorId(creatorId);
        Blog blog = blogRepository.findByBlogId(blogUpdateForm.getId())
                .orElseThrow(() -> new IllegalArgumentException(NO_BLOG.message));
        blog.setTitle(blogUpdateForm.getTitle());
        blog.setContents(blogUpdateForm.getContents());
        return blogRepository.update(blog);
    }

    public void validateCreatorId(Long creatorId) {
        memberService.isValidCreatorId(creatorId);
    }
}
