package com.landvibe.landlog.repository;

import java.util.List;
import java.util.Optional;

import com.landvibe.landlog.domain.Blog;

public interface BlogRepository {
	void save(Blog blog);

	void update(Blog blog);

	void delete(Long blogId);

	Optional<Blog> findByBlogId(Long blogId);

	List<Blog> findBlogListByCreatorId(Long creatorId);

}
