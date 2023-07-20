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
    private static long blogSequence = 0L;

    @Override
    public void save(Blog blog) {
        blog.setId(++blogSequence);
        store.put(blog.getId(), blog);
    }

    @Override
    public Optional<Blog> findById(Long blogId) {
        return Optional.ofNullable(store.get(blogId));
    }

    @Override
    public void deleteById(Long blogId) {
        store.remove(blogId);
    }

    @Override
    public List<Blog> findBlogByCreatorId(Long creatorId) {
        return store.values().stream()
                .filter(blog -> blog.getCreatorId() == creatorId)
                .toList();
    }

    public void clearStore() {
        store.clear();
    }
}
