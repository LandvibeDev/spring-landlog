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
        blog.setId(++sequence);
        store.put(sequence, blog);
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
        Blog updatedBlog = new Blog(title, memberId, content);
        updatedBlog.setId(id);
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
    }

    @Override
    public int getSize() {
        return store.size();
    }

}
