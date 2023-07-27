package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogForm;

import java.util.List;
import java.util.Optional;

public interface BlogRepository {

    Blog save(Blog blog);

    void update(Long blogId, BlogForm updatedForm);

    Optional<Blog> findBlogByMemberAndBlogId(Long memberId, Long blogId);

    List<Blog> findAllBlogsByCreatorId(Long creatorId);
}
