package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;

import java.util.List;
import java.util.Optional;

public interface BlogRepository {
    Blog save(Long creatorId, String title, String contents);

    Optional<Blog> findById(Long blogId);

    List<Blog> findByCreatorId(Long creatorId);

    Blog modify(Long blogId, String title, String contents, Long creatorId);

    void erase(Long blogId);
}
