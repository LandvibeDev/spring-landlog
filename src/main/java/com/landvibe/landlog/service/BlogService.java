package com.landvibe.landlog.service;

import com.landvibe.landlog.Message;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogForm;
import com.landvibe.landlog.form.MemberLoginForm;
import com.landvibe.landlog.domain.Member;
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

    public Long create(Blog blog) {
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
