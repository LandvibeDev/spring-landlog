package com.landvibe.landlog.repository;

import com.landvibe.landlog.controller.UpdateBlogForm;
import com.landvibe.landlog.domain.Blog;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
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
    public List<Blog> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Blog> findAllByCreatorId(Long id) {
        return store.values().stream()
                .filter(b -> id.equals(b.getCreatorId()))
                .collect(Collectors.toList());
    }

    @Override
    public Blog findOneByBlogIdAndCreatorId(Long blogId, Long creatorId) {
        return store.values().stream()
                .filter(b -> blogId.equals(b.getId()) && creatorId.equals(b.getCreatorId()))
                .findAny().orElseThrow(() -> new IllegalArgumentException("해당 블로그가 존재하지 않습니다."));
    }

    @Override
    public void deleteBlog(Long blogId, Long creatorId) {
        store.remove((store.values().stream()
                .filter(b -> b.getId() == blogId && b.getCreatorId() == creatorId)
                .findAny().orElseThrow(() -> new IllegalArgumentException("해당 블로그가 존재하지 않습니다.")).getId()));
    }

    public void clearStore() {
        store.clear();
    }
}
