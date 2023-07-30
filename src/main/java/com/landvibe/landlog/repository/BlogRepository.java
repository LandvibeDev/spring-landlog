package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;

import java.util.List;
import java.util.Optional;

public interface BlogRepository {
    Long save(Long memberId, String title, String contents);

    Optional<Blog> findById(Long blogId);

    List<Blog> findByCreatorId(Long creatorId);

    void modify(Long blogId, String title, String contents, Long memberId);

    void erase(Long blogId);
}
