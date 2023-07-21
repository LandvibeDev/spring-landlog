package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.dto.BlogForm;
import com.landvibe.landlog.dto.BlogUpdateForm;

import java.util.List;
import java.util.Optional;

public interface BlogRepository {
    void save(Blog blog);

    void delete(Long blogId);

    void update(Blog blog);

    Optional<Blog> findByBlogId(Long blogId);

    Optional<Blog> findByCreatorIdAndBlogId(Long creatorId, Long blogId);

    List<Blog> findByCreatorId(Long creatorId);

}
