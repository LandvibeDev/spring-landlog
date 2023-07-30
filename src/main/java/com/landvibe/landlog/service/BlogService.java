package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogForm;
import com.landvibe.landlog.repository.BlogRepository;
import org.springframework.stereotype.Service;

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

        return blogRepository.findByBlogId(blogId).get();
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
        if(creatorId <= 0L) throw new IllegalArgumentException("잘못된 회원 id 입니다.");

        memberService.findMemberById(creatorId);
    }

    private void validBlogId(Long blogId){
        if(blogId <= 0L) throw new IllegalArgumentException("잘못된 게시물 id 입니다.");

        blogRepository.findByBlogId(blogId).
                orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 입니다."));
    }

    public void validateBlogForm(String title, String contents) {
        if (title.equals("")) {
            throw new IllegalArgumentException("제목을 입력해주세요.");
        }
        if (contents.equals("")) {
            throw new IllegalArgumentException("내용을 입력해주세요.");
        }
    }

    public List<Blog> findBlogs(Long creatorId) {
        return blogRepository.findAllBlogsByCreatorId(creatorId);
    }
}
