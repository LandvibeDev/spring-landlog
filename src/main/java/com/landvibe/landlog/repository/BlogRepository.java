package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;

import java.util.List;
import java.util.Optional;

public interface BlogRepository {

    Optional<Blog> findById(Long id);

    List<Blog> findByCreator(Long creatorId);

    Blog create(Blog blog);

    Blog update(Blog blog);

    void delete(Long id);
}
