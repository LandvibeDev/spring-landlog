package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogForm;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryBlogRepository implements BlogRepository {
    private static Map<Long, Blog> blogStore = new HashMap<>();
    private static long blogNumber = 0L;

    @Override
    public Blog save(Blog blog) {
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
    public void update(Long blogId, BlogForm form) {
        blogStore.get(blogId).setTitle(form.getTitle());
        blogStore.get(blogId).setContents(form.getContents());
    }

    @Override
    public void delete(Long deleteBlogId){
        blogStore.remove(deleteBlogId);
    }
    @Override
    public List<Blog> findAllBlogsByCreatorId(Long creatorId) {
        List<Blog> creatorBlogList = new ArrayList<>();

        for (Map.Entry<Long, Blog> entry : blogStore.entrySet()) {
            Blog blog = entry.getValue();
            if(blog.getCreatorId() == creatorId){
                creatorBlogList.add(blog);
            }
        }

        return creatorBlogList;
    }
}
