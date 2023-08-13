package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.exceptionHandler.IllegalBlogIdException;
import com.landvibe.landlog.exceptionHandler.IllegalCreatorIdException;
import com.landvibe.landlog.exceptionHandler.NoValidBlogFormException;
import com.landvibe.landlog.form.BlogForm;
import com.landvibe.landlog.repository.BlogRepository;
import org.springframework.stereotype.Service;
import static com.landvibe.landlog.exceptionHandler.ErrorCode.*;

import java.util.List;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final MemberService memberService;

    public BlogService(BlogRepository blogRepository, MemberService memberService) {
        this.blogRepository = blogRepository;
        this.memberService = memberService;
    }

    public Blog register(Long creatorId, BlogForm form) {
        validCreatorId(creatorId);
        validateBlogForm(form.getTitle(), form.getContents());

        return blogRepository.save(creatorId, form);
    }

    public Blog findBlogById(Long blogId){
        validBlogId(blogId);
        Blog blog = blogRepository.findBlogByBlogId(blogId).get();
        validCreatorId(blog.getCreatorId());
        return blog;
    }

    public Blog update(Long creatorId, Long blogId, BlogForm form){
        validBlogId(blogId);
        validCreatorId(creatorId);
        validateBlogForm(form.getTitle(), form.getContents());

        return blogRepository.update(blogId, form);
    }

    public boolean delete(Long creatorId, Long blogId){
        validCreatorId(creatorId);
        validBlogId(blogId);
        blogRepository.delete(blogId);
        return true;
    }

    public void validCreatorId(Long creatorId){
        if(creatorId <= 0) throw new IllegalCreatorIdException(NO_MATCH_MEMBERID_EXCEPTION);
        memberService.findMemberById(creatorId);
    }

    private void validBlogId(Long blogId){
        if(blogId <= 0) throw new IllegalBlogIdException(NO_MATCH_BLOGID_EXCEPTION);
        blogRepository.findBlogByBlogId(blogId).
                orElseThrow(() -> new IllegalBlogIdException(NO_MATCH_BLOGID_EXCEPTION));
    }

    public void validateBlogForm(String title, String contents) {
        if (title.equals("")) {
            throw new NoValidBlogFormException(NO_VALID_BLOG_TITLE);
        }
        if (contents.equals("")) {
            throw new NoValidBlogFormException(NO_VALID_BLOG_CONTENTS);
        }
    }

    public List<Blog> findBlogs(Long creatorId) {
        validCreatorId(creatorId);
        return blogRepository.findAllBlogsByCreatorId(creatorId);
    }
}
