package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.dto.BlogUpdateForm;
import com.landvibe.landlog.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.landvibe.landlog.ErrorMessage.NO_BLOG;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public void registerBlog(Blog blog) {
        blogRepository.save(blog);
    }

    public List<Blog> findBlogsByCreatorId(Long creatorId) {
        return blogRepository.findByCreatorId(creatorId);
    }

    public Blog findById(Long blogId) {
        return blogRepository.findByBlogId(blogId)
                .orElseThrow(() -> new IllegalArgumentException(NO_BLOG.message));
    }

    public void deleteBlog(Long blogId, Long creatorId) {
        blogRepository.findByCreatorIdAndBlogId(blogId, creatorId)
                .orElseThrow(() -> new IllegalArgumentException(NO_BLOG.message));
        blogRepository.delete(blogId);
    }

    public void updateBlog(BlogUpdateForm blogUpdateForm, Long creatorId) {
        Blog blog = blogRepository.findByCreatorIdAndBlogId(blogUpdateForm.getId(), creatorId)
                .orElseThrow(() -> new IllegalArgumentException(NO_BLOG.message));
        blog.setTitle(blogUpdateForm.getTitle());
        blog.setContents(blogUpdateForm.getContents());
        blogRepository.update(blog);
    }
}
