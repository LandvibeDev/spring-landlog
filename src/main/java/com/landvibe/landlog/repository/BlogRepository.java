package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;

import java.util.List;
import java.util.Optional;

public interface BlogRepository {
    void save(Blog blog);

    void delete(Long id);

    Blog update(Long id, Blog blog);

    Optional<Blog> findById(Long id);

    List<Blog> findByCreatorId(Long creatorId);

}