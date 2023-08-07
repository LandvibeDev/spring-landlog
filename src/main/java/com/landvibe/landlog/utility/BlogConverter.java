package com.landvibe.landlog.utility;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogCreateForm;
import com.landvibe.landlog.form.BlogUpdateForm;

public class BlogConverter {

    public static BlogUpdateForm getBlogUpdateForm(Blog blog) {
        BlogUpdateForm blogUpdateForm = BlogUpdateForm.builder()
                .contents(blog.getContents())
                .title(blog.getTitle())
                .build();
        return blogUpdateForm;
    }

    public static BlogCreateForm getBlogCreateForm(Blog blog) {
        BlogCreateForm blogCreateForm = BlogCreateForm.builder()
                .contents(blog.getContents())
                .title(blog.getTitle())
                .build();
        return blogCreateForm;
    }

    public static Blog getBlogFromCreateForm(BlogCreateForm form, Long creatorId) {
        String title = form.getTitle();
        String content = form.getContents();
        Blog blog = Blog.builder()
                .title(title)
                .contents(content).
                creatorId(creatorId)
                .build();
        return blog;
    }

    public static Blog getBlogFromUpdateForm(BlogUpdateForm form, Long creatorId, Long id) {
        String title = form.getTitle();
        String content = form.getContents();
        Blog updatedBlog = Blog.builder()
                .title(title)
                .contents(content)
                .id(id)
                .creatorId(creatorId)
                .build();
        return updatedBlog;
    }

}
