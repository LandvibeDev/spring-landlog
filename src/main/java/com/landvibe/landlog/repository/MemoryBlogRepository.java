package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogUpdateForm;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryBlogRepository implements BlogRespository {

    private static Map<Long, Blog> store = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public Long save(Blog blog) {
        String title = blog.getTitle();
        String content = blog.getContents();
        Long creatorId = blog.getCreatorId();
        Blog newBlog = Blog.builder()
                .id(++sequence)
                .creatorId(creatorId)
                .contents(content)
                .title(title)
                .build();
        store.put(sequence, newBlog);
        return sequence;
    }

    @Override
    public Long delete(Long id) {
        store.remove(id);
        return id;
    }

    @Override
    public Long update(Long id, BlogUpdateForm form) {
        Blog blog = store.get(id);
        String title = form.getTitle();
        String content = form.getContents();
        Long memberId = blog.getCreatorId();
        Blog updatedBlog = Blog.builder()
                .title(title)
                .contents(content)
                .creatorId(memberId)
                .id(id)
                .build();
        store.replace(id, updatedBlog);
        return id;
    }

    @Override
    public List<Blog> findAllByMemberId(Long memberId) {
        List<Blog> blogList = new ArrayList<>();
        return store.values().stream()
                .filter(blog -> blog.getCreatorId() == memberId).toList();
    }

    @Override
    public Optional<Blog> findByBlogId(Long blogId) {
        Optional<Blog> blog = Optional.ofNullable(store.get(blogId));
        return blog;
    }

    @Override
    public void clear() {
        store.clear();
        sequence = 0L;
    }

    @Override
    public int getSize() {
        return store.size();
    }

}
