package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogForm;
import com.landvibe.landlog.repository.BlogRepository;
import com.landvibe.landlog.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final MemberRepository memberRepository;

    public BlogService(BlogRepository blogRepository, MemberRepository memberRepository) {
        this.blogRepository = blogRepository;
        this.memberRepository = memberRepository;
    }

    public void register(Long creatorId, Blog blog) {
        validCreatorId(creatorId);
        validBlogId(creatorId, blog.getId());
        blogRepository.save(blog);
    }

    public Blog findBlogByBlogIdAndCreatorId(Long creatorId, Long blogId){
        validCreatorId(creatorId);
        validBlogId(creatorId, blogId);

        return blogRepository.findBlogByCreatorIdAndBlogId(creatorId,blogId).get();
    }

    public void updateBlog(Long creatorId, Long blogId, BlogForm form){
        validCreatorId(creatorId);
        validBlogId(creatorId, blogId);
        blogRepository.update(blogId, form);
    }

    public void deleteBlog(Long creatorId, Long blogId){
        validCreatorId(creatorId);
        validBlogId(creatorId, blogId);
        blogRepository.delete(blogId);
    }

    private void validCreatorId(Long creatorId){
        memberRepository.findById(creatorId).
                orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 id 입니다."));
    }

    private void validBlogId(Long creatorId, Long blogId){
        blogRepository.findBlogByCreatorIdAndBlogId(creatorId, blogId).
                orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 입니다."));
    }

    public List<Blog> findBlogs(Long creatorId) {
        return blogRepository.findAllBlogsByCreatorId(creatorId);
    }
}
