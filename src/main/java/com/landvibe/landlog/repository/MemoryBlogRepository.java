package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Blog> findBlogsByCreatorId(Long creatorId) {
        return store.values().stream()
                .filter(blog -> blog.getCreatorId() == creatorId)
                .toList();
    }
}
