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
        return blogRepository.findBlogByCreatorId(creatorId);
    }

    public Blog findById(Long blogId) {
        return blogRepository.findById(blogId)
                .orElseThrow(() -> new IllegalArgumentException(NO_BLOG.message));
    }

    public void deleteById(Long blogId) {
        blogRepository.deleteById(blogId);
    }

    public void updateBlog(BlogUpdateForm blogUpdateForm) {
        Blog blog = blogRepository.findById(blogUpdateForm.getBlogId())
                .orElseThrow(() -> new IllegalArgumentException(NO_BLOG.message));
        blog.setTitle(blogUpdateForm.getTitle());
        blog.setContents(blogUpdateForm.getContents());
    }
}
