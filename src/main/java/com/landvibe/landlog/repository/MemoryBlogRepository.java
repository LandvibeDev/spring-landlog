package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryBlogRepository implements BlogRepository {
    private static Map<Long, Blog> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Blog save(Blog blog) {
        blog.setId(++sequence);
        store.put(blog.getId(), blog);
        return blog;
    }

    @Override
    public Blog update(Blog blog) {
        store.put(blog.getId(), blog);
        return blog;
    }

    @Override
    public void delete(Blog blog) {
        store.remove(blog.getId());
    }

    @Override
    public List<Blog> findBlogsByCreatorId(Long creatorId) {
        return store.values().stream()
                .filter(blog -> blog.getCreatorId() == creatorId)
                .toList();
    }

    @Override
    public Optional<Blog> findBlogByBlogId(Long blogId) {
        return Optional.ofNullable(store.get(blogId));
    }

    public void clearStore() {
        store.clear();
    }
}