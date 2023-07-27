package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;

import java.util.List;

public interface BlogRepository {
    Blog save(Blog blog);

    List<Blog> findBlogsByCreatorId(Long creatorId);
}
