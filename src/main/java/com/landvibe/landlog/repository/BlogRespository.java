package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogUpdateForm;

import java.util.List;
import java.util.Optional;

public interface BlogRespository {

    public Long save(Blog blog);

    public Long delete(Long id);

    public Long update(Long id, BlogUpdateForm form);

    public List<Blog> findAllByMemberId(Long memberId);

    public Blog findByBlogId(Long blogId);

    void clear();
}