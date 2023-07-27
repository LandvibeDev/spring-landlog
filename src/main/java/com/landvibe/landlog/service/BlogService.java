package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.form.BlogForm;
import com.landvibe.landlog.repository.BlogRepository;
import com.landvibe.landlog.repository.MemberRepository;
import com.landvibe.landlog.repository.MemoryBlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final MemberRepository memberRepository;

    public BlogService(BlogRepository blogRepository, MemberRepository memberRepository) {
        this.blogRepository = blogRepository;
        this.memberRepository = memberRepository;
    }

    public void register(Long creatorId, Blog blog) {
        memberRepository.findById(creatorId).
                orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 id 입니다."));
        blogRepository.save(blog);
    }

    public Blog findBlogByBlogIdAndCreatorId(Long creatorId, Long blogId){
        Optional<Blog> blog = blogRepository.findBlogByCreatorIdAndBlogId(creatorId, blogId);
        blog.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 입니다."));

        return blog.get();
    }

    public void updateBlog(Long creatorId, Long blogId, BlogForm form){
        blogRepository.update(blogId, form);
    }

    public void deleteBlog(Long creatorId, Long blogId){
        blogRepository.delete(creatorId, blogId);
    }
    public List<Blog> findBlogs(Long creatorId) {
        return blogRepository.findAllBlogsByCreatorId(creatorId);
    }
}
