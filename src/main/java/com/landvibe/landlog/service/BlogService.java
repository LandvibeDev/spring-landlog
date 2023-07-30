package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.exception.LandLogException;
import com.landvibe.landlog.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.landvibe.landlog.exception.ErrorCode.NOT_FOUND_BLOG;

@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final MemberService memberService;

    public BlogService(BlogRepository blogRepository, MemberService memberService) {
        this.blogRepository = blogRepository;
        this.memberService = memberService;
    }

    // <-- 수정,생성 -->
    public Long createBlog(Long creatorId, String title, String contents) {
        memberService.findMemberById(creatorId); // 예외

        return blogRepository.save(creatorId, title, contents);
    }

    public void updateBlog(Long creatorId, Long blogId, String title, String contents) {
        memberService.findMemberById(creatorId); // 예외
        findBlogByBlogIdAndCreatorId(creatorId, blogId); // 예외

        blogRepository.modify(blogId, title, contents, creatorId);
    }

    public void deleteBlog(Long creatorId, Long blogId) {
        memberService.findMemberById(creatorId); // 예외
        findBlogByBlogIdAndCreatorId(creatorId, blogId); // 예외

        blogRepository.erase(blogId);
    }

    // <--조회-->
    public List<Blog> findBlogsByCreatorId(Long creatorId) {
        return blogRepository.findByCreatorId(creatorId); // 예외
    }

    public Blog findBlogByBlogIdAndCreatorId(Long creatorId, Long blogId) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new LandLogException(NOT_FOUND_BLOG)); // 예외
        validateCreatorId(creatorId, blog); // 예외
        return blog;
    }

    // <--valid-->
    private void validateCreatorId(Long creatorId, Blog blog) {
        if (!creatorId.equals(blog.getCreatorId())) {
            throw new LandLogException(NOT_FOUND_BLOG);
        }
    }

}
