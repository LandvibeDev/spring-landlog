package com.landvibe.landlog.service;

import com.landvibe.landlog.Message;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogForm;
import com.landvibe.landlog.form.BlogUpdateForm;
import com.landvibe.landlog.repository.BlogRepository;
import com.landvibe.landlog.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    private final MemberRepository memberRepository;
    private final BlogRepository blogRepository;

    public BlogService(MemberRepository memberRepository, BlogRepository blogRepository) {
        this.memberRepository = memberRepository;
        this.blogRepository = blogRepository;
    }

    public List<Blog> findBlogsByCreatorId(Long creatorId) {
        validateCreatorId(creatorId);
        return blogRepository.findBlogsByCreatorId(creatorId);
    }

    public Blog findBlogByBlogId(Long blogId) {
        return blogRepository.findBlogByBlogId(blogId)
                .orElseThrow(() -> new IllegalArgumentException(Message.NO_BLOG.message));
    }

    public Long create(Long creatorId, BlogForm form) {
        validateCreatorId(creatorId);
        Blog blog = new Blog(form.getTitle(), form.getContents(), creatorId);
        validateEmptyInput(blog);
        blogRepository.save(blog);
        return blog.getId();
    }

    public void update(Long creatorId, BlogUpdateForm form) {
        validateCreatorId(creatorId);
        Blog blog = findBlogByBlogId(form.getId());
        blog.setTitle(form.getTitle());
        blog.setContents(form.getContents());
        validateEmptyInput(blog);
        blogRepository.update(blog);
    }

    public void delete(Long creatorId, Long blogId) {
        validateCreatorId(creatorId);
        blogRepository.delete(findBlogByBlogId(blogId));
    }

    private void validateCreatorId(Long creatorId) {
        memberRepository.findById(creatorId)
                .orElseThrow(() -> new IllegalArgumentException(Message.NO_USER.message));
    }

    private void validateEmptyInput(Blog blog) {
        if (blog.getContents().equals("")) {
            throw new IllegalArgumentException(Message.NO_INPUT_NAME.message);
        }
        if (blog.getTitle().equals("")) {
            throw new IllegalArgumentException(Message.NO_INPUT_EMAIL.message);
        }
    }
}
