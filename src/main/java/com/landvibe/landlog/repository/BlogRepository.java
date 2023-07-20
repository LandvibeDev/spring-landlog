package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.dto.BlogUpdateForm;

import java.util.List;
import java.util.Optional;

public interface BlogRepository {
    void save(Blog blog);

    void deleteById(Long blogId);

    Optional<Blog> findById(Long blogId);

    List<Blog> findBlogByCreatorId(Long creatorId);
}
