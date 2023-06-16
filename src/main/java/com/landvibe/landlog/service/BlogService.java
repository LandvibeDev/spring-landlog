package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Optional<Blog> findBlog(Long id) {
        return blogRepository.findById(id);
    }

    public List<Blog> findBlogsByCreator(Long creatorId) {
        return blogRepository.findByCreator(creatorId);
    }

    public Blog createBlog(Blog blog) {
        return blogRepository.create(blog);
    }

    public Blog updateBlog(Blog blog) {
        return blogRepository.update(blog);
    }

    public void deleteBlog(Long id) {
        blogRepository.delete(id);
    }

}
