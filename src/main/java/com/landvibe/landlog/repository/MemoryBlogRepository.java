package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MemoryBlogRepository implements BlogRepository {

    private static Map<Long, Blog> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Optional<Blog> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Blog> findByCreator(Long creatorId) {
        return store.values().stream()
                .filter(blog -> blog.getCreatorId().equals(creatorId))
                .collect(Collectors.toList());
    }

    @Override
    public Blog create(Blog blog) {
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
    public void delete(Long id) {
        store.remove(id);
    }

    public void clearStore() {
        store.clear();
    }
}
