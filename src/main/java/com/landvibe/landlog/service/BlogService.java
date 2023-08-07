package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogCreateForm;
import com.landvibe.landlog.form.BlogUpdateForm;
import com.landvibe.landlog.repository.BlogRespository;
import com.landvibe.landlog.utility.BlogConverter;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {


    private final BlogRespository blogRespository;

    public BlogService(BlogRespository blogRespository) {
        this.blogRespository = blogRespository;
    }


    public void createBlog(Long creatorId, BlogCreateForm form) {
        Blog blog = BlogConverter.getBlogFromCreateForm(form, creatorId);
        blogRespository.save(blog);
    }

    public List<Blog> findAllBlogs(Long creatorId) {
        List<Blog> blogList = blogRespository.findAllByMemberId(creatorId);
        return blogList;
    }

    public Blog findBlogById(Long blogId) {
        return blogRespository.findByBlogId(blogId)
                .orElseThrow(() -> new IllegalStateException("해당하는 블로그를 찾을 수 없습니다."));
    }

    public Long update(Long id, BlogUpdateForm form) {
        findBlogById(id);
        return blogRespository.update(id, form);
    }

    public Long delete(Long blogId) {
        findBlogById(blogId);
        return blogRespository.delete(blogId);
    }

}

