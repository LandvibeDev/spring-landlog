package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.repository.BlogRepository;
import com.landvibe.landlog.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import static com.landvibe.landlog.ErrorMessage.*;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final MemberRepository memberRepository;

    public BlogService(BlogRepository blogRepository, MemberRepository memberRepository) {
        this.blogRepository = blogRepository;
        this.memberRepository = memberRepository;
    }

    public void validateCreatorId(Long creatorId) {
        if (creatorId == null || creatorId == 0) {
            throw new IllegalArgumentException(EMPTY_CREATOR_ID.message);
        }

        memberRepository.findById(creatorId).orElseThrow(() ->
                new IllegalArgumentException(NO_MATCH_MEMBER_WITH_CREATOR_ID.message));
    }

    public void validateBlogId(Long blogId) {
        if (blogId == null || blogId == 0) {
            throw new IllegalArgumentException(EMPTY_BLOG_ID.message);
        }
        blogRepository.findById(blogId).orElseThrow(() ->
                new IllegalArgumentException(NO_MATCH_BLOG_WITH_BLOG_ID.message));
    }

    public Long write(Blog blog) throws IllegalArgumentException {
        validateCreatorId(blog.getCreatorId());
        blogRepository.save(blog);
        return blog.getId();
    }

    public Long update(Blog blog) throws IllegalArgumentException {
        validateCreatorId(blog.getCreatorId());
        blogRepository.update(blog);
        return blog.getId();
    }

    public List<Blog> findBlogsByCreatorId(Long creatorId) throws IllegalArgumentException {
        validateCreatorId(creatorId);
        return blogRepository.findBlogsByCreatorId(creatorId);
    }

    public Blog findById(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new IllegalArgumentException(NO_MATCH_BLOG_WITH_BLOG_ID.message));
        return blog;
    }

    public Long deleteById(Long blogId) throws IllegalArgumentException {
        validateCreatorId(findById(blogId).getCreatorId());
        Long id = blogRepository.deleteById(blogId);
        return id;
    }
}
