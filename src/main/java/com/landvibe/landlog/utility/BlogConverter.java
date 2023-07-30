package com.landvibe.landlog.utility;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogCreateForm;
import com.landvibe.landlog.form.BlogUpdateForm;

public class BlogConverter {

    public static BlogUpdateForm getBlogUpdateForm(Blog blog) {
        BlogUpdateForm blogUpdateForm = new BlogUpdateForm(blog.getTitle(), blog.getContents());
        return blogUpdateForm;
    }

    public static BlogCreateForm getBlogCreateForm(Blog blog) {
        BlogCreateForm blogCreateForm = new BlogCreateForm(blog.getTitle(), blog.getContents());
        return blogCreateForm;
    }
}
