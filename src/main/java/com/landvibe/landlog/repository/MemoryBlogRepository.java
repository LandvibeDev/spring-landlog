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
    public Blog save(Long creatorId, String title, String contents) {
        Blog blog = Blog.createBlog(title, contents, creatorId);
        blog.setId(++sequence);
        store.put(blog.getId(), blog);

        return blog;
    }

    @Override
    public Optional<Blog> findById(Long blogId) {
        return Optional.ofNullable(store.get(blogId));
    }

    @Override
    public List<Blog> findByCreatorId(Long creatorId) {
        return store.values().stream()
                .filter(blog -> blog.getCreatorId().equals(creatorId)).collect(Collectors.toList());
    }

    @Override
    public Blog modify(Long blogId, String title, String contents, Long creatorId) {
        Blog blog = Blog.createBlog(title, contents, creatorId);
        blog.setId(blogId);
        store.replace(blogId, blog);

        return blog;
    }

    @Override
    public void erase(Long blogId) {
        store.remove(blogId);
    }

    public void clearStore() {
        store.clear();
    }
}
