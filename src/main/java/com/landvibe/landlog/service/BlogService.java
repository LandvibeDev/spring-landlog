package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogCreateForm;
import com.landvibe.landlog.form.BlogUpdateForm;
import com.landvibe.landlog.repository.BlogRespository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {


    private final BlogRespository blogRespository;

    public BlogService(BlogRespository blogRespository) {
        this.blogRespository = blogRespository;
    }


    public void createBlog(Long creatorId, BlogCreateForm form) {
        String title = form.getTitle();
        String content = form.getContents();
        Blog blog = new Blog(title, creatorId, content);
        blogRespository.save(blog);
    }

    public List<Blog> findAllBlogs(Long creatorId) {
        List<Blog> blogList = blogRespository.findAllByMemberId(creatorId);
        return blogList;
    }

    public Blog findBlogById(Long blogId) {
        return blogRespository.findByBlogId(blogId);
    }

    public Long update(Long id, BlogUpdateForm form) {
        return blogRespository.update(id, form);
    }

    public Long delete(Long blogId) {
        return blogRespository.delete(blogId);
    }


}
