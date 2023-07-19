package com.landvibe.landlog.repository;

import com.landvibe.landlog.controller.UpdateBlogForm;
import com.landvibe.landlog.domain.Blog;
import java.util.List;
import java.util.Optional;

public interface BlogRepository {
    public Blog save(Blog blog);

    public List<Blog> findAll();

    public List<Blog> findAllByCreatorId(Long id);

    public Blog findOneByBlogIdAndCreatorId(Long blogId, Long creatorId);

    public void deleteBlog(Long blogId, Long creatorId);
}
