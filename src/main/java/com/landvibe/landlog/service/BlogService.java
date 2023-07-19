package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.UpdateBlogForm;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public List<Blog> findBlogs(Long creatorId) {
        return new ArrayList<>(blogRepository.findAllByCreatorId(creatorId));
    }

    public Blog findOne(Long blogId, Long creatorId) {
        return blogRepository.findOneByBlogIdAndCreatorId(blogId, creatorId);
    }

    public void update(Long blogId, Long creatorId, UpdateBlogForm updateParam) {
        Blog findBlog = blogRepository.findOneByBlogIdAndCreatorId(blogId, creatorId);
        findBlog.setTitle(updateParam.getTitle());
        findBlog.setContents(updateParam.getContents());
    }

    public Long create(Blog blog) {
        blogRepository.save(blog);
        return blog.getId();
    }

    public void delete(Long blogId, Long creatorId){
        blogRepository.deleteBlog(blogId,creatorId);
    }

}
