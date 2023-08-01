package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogForm;
import com.landvibe.landlog.repository.BlogRepository;
import org.springframework.stereotype.Service;
import static com.landvibe.landlog.validator.ErrorMassage.*;

import java.util.List;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final MemberService memberService;

    public BlogService(BlogRepository blogRepository, MemberService memberService) {
        this.blogRepository = blogRepository;
        this.memberService = memberService;
    }

    public Long register(Long creatorId, BlogForm form) {
        validCreatorId(creatorId);
        validateBlogForm(form.getTitle(), form.getContents());

        Blog blog = new Blog(creatorId, form.getTitle(), form.getContents());

        return blogRepository.save(blog);
    }

    public Blog findBlogByBlogId(Long blogId){
        validBlogId(blogId);

        return blogRepository.findBlogByBlogId(blogId).get();
    }

    public Long updateBlog(Long blogId, BlogForm form){
        validBlogId(blogId);
        validateBlogForm(form.getTitle(), form.getContents());

        blogRepository.update(blogId, form);

        return blogId;
    }

    public boolean deleteBlog(Long blogId){
        validBlogId(blogId);
        blogRepository.delete(blogId);
        return true;
    }

    private void validCreatorId(Long creatorId){
        memberService.findMemberById(creatorId);
    }

    private void validBlogId(Long blogId){
        blogRepository.findBlogByBlogId(blogId).
                orElseThrow(() -> new IllegalArgumentException(NO_MATCH_BLOGID_EXCEPTION.getMessage()));
    }

    public void validateBlogForm(String title, String contents) {
        if (title.equals("")) {
            throw new IllegalArgumentException(NO_VALID_BLOG_TITLE.getMessage());
        }
        if (contents.equals("")) {
            throw new IllegalArgumentException(NO_VALID_BLOG_CONTENTS.getMessage());
        }
    }

    public List<Blog> findBlogs(Long creatorId) {
        return blogRepository.findAllBlogsByCreatorId(creatorId);
    }
}
