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
}
