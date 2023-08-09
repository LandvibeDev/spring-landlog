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
    public Blog save(Long creatorId, BlogForm blogForm) {
        Blog blog = Blog.builder()
                .creatorId(creatorId)
                .title(blogForm.getTitle())
                .contents(blogForm.getContents())
                .build();

        blog.setId(++blogNumber);

        blogStore.put(blog.getId(), blog);
        return blog;
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
    public Blog update(Long blogId, BlogForm form) {
        Blog updatedBlog = blogStore.get(blogId);

        updatedBlog.setTitle(form.getTitle());
        updatedBlog.setContents(form.getContents());

        return updatedBlog;
    }

    @Override
    public boolean delete(Long deleteBlogId) {
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
