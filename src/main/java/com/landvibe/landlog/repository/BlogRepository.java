package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogForm;

import java.util.List;
import java.util.Optional;

public interface BlogRepository {

    Long save(Long creatorId, BlogForm blogForm);

    Long update(Long blogId, BlogForm updatedForm);

    boolean delete(Long blogId);

    Optional<Blog> findBlogByCreatorIdAndBlogId(Long memberId, Long blogId);

    Optional<Blog> findBlogByBlogId(Long blogId);

    List<Blog> findAllBlogsByCreatorId(Long creatorId);
}
