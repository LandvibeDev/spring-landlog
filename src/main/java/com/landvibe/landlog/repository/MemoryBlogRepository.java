package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogForm;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MemoryBlogRepository implements BlogRepository {
    private static Map<Long, Blog> blogStore = new HashMap<>();
    private static long blogNumber = 0L;

    @Override
    public Long save(Long creatorId, BlogForm blogForm) {
        Blog blog = new Blog(creatorId, blogForm.getTitle(), blogForm.getContents());
        blog.setId(++blogNumber);

        blogStore.put(blog.getId(), blog);
        return blog.getId();
    }

    @Override
    public Optional<Blog> findBlogByCreatorIdAndBlogId(Long creatorId, Long BlogId) {
        return blogStore.values().stream()
                .filter(blog -> blog.getCreatorId().equals(creatorId) && blog.getId().equals(BlogId))
                .findAny();
    }

    @Override
    public Optional<Blog> findBlogByBlogId(Long blogId) {
        return Optional.ofNullable(blogStore.get(blogId));
    }

    @Override
    public Long update(Long blogId, BlogForm form) {
        blogStore.get(blogId).setTitle(form.getTitle());
        blogStore.get(blogId).setContents(form.getContents());
        return blogId;
    }

    @Override
    public boolean delete(Long deleteBlogId){
        blogStore.remove(deleteBlogId);
        return true;
    }

    @Override
    public List<Blog> findAllBlogsByCreatorId(Long creatorId) {
        return blogStore.values().stream()
                .filter(blog -> blog.getCreatorId().equals(creatorId))
                .collect(Collectors.toList());
    }

    public void clearStore() {
        blogStore.clear();
    }
}
