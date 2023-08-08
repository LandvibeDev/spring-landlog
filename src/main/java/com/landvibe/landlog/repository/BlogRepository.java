package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;

import java.util.List;
import java.util.Optional;

public interface BlogRepository {
    Blog save(Blog blog);

    Blog update(Blog blog);

    void delete(Blog blog);

    List<Blog> findBlogsByCreatorId(Long creatorId);

    Optional<Blog> findBlogByBlogId(Long blogId);
}
