package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;

import java.util.List;
import java.util.Optional;

public interface BlogRepository {
    Blog save(Blog blog);

    Blog update(Blog blog);

    Optional<Blog> findById(Long id);

    List<Blog> findBlogsByCreatorId(Long id);

    Long deleteById(Long blogId);
<<<<<<< HEAD

    boolean existsById(Long blogId);
=======
>>>>>>> bde545b (add blogRepository)
}
