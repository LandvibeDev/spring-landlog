package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

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
        store.replace(blog.getId(), blog);
        return blog;
    }

    @Override
    public Optional<Blog> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Long deleteById(Long blogId) {
        store.remove(blogId);
        return blogId;
    }

    @Override
    public List<Blog> findBlogsByCreatorId(Long id) {
        return store.values().stream()
                .filter(blog -> blog.getCreatorId().equals(id))
                .collect(Collectors.toList());
    }

    public void clearStore() {
        store.clear();
    }
}
