package com.landvibe.landlog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.landvibe.landlog.domain.Blog;

@Repository
public class MemoryBlogRepository implements BlogRepository {
	private static Map<Long, Blog> store = new HashMap<>();
	private static long sequence = 0L;

	@Override
	public void save(Blog blog) {
		blog.setId(++sequence);
		store.put(blog.getId(), blog);
	}

	@Override
	public void update(Blog blog) {
		store.put(blog.getId(), blog);
	}
	@Override
	public void delete(Long blogId) {
		store.remove(blogId);
	}

	@Override
	public List<Blog> findBlogListByCreatorId(Long creatorId) {

	}
	@Override
	public Optional<Blog> findByBlogId(Long blogId) {
		return Optional.ofNullable(store.get(blogId));
	}

	public void clearStore() {
		store.clear();
	}
}
