package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.exception.BlogException;
import com.landvibe.landlog.exception.MemberException;
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
            throw new MemberException(EMPTY_CREATOR_ID.message);
        }

        boolean exists = memberRepository.existsById(creatorId);
        if (!exists) {
            throw new MemberException(NO_MATCH_MEMBER_WITH_CREATOR_ID.message);
        }
    }

    public void validateBlogId(Long blogId) {
        if (blogId == null || blogId == 0) {
            throw new BlogException(EMPTY_BLOG_ID.message);
        }

        boolean exists = blogRepository.existsById(blogId);
        if (!exists) {
            throw new BlogException(NO_MATCH_BLOG_WITH_BLOG_ID.message);
        }
    }

    public Blog write(Blog blog) throws MemberException {
        validateCreatorId(blog.getCreatorId());
        blogRepository.save(blog);
        return blog;
    }

    public Blog update(Blog blog) throws MemberException {
        validateCreatorId(blog.getCreatorId());
        blogRepository.update(blog);
        return blog;
    }

    public List<Blog> findBlogsByCreatorId(Long creatorId) throws MemberException {
        validateCreatorId(creatorId);
        return blogRepository.findBlogsByCreatorId(creatorId);
    }

    public Blog findById(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new BlogException(NO_MATCH_BLOG_WITH_BLOG_ID.message));
        return blog;
    }

    public Long deleteById(Long blogId) throws MemberException, BlogException {
        validateCreatorId(findById(blogId).getCreatorId());
        Long id = blogRepository.deleteById(blogId);
        return id;
    }
}