package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogUpdateForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemoryBlogRepositoryTest {

    String title = "title";
    String contents = "contents";

    String afterUpdateTitle = "afterUpdateTitle";

    String afterUpdateContents = "afterUpdateContents";
    Long blogId = 1L;

    BlogRespository blogRespository = new MemoryBlogRepository();
    Blog blog = new Blog(title, blogId, contents);
    Blog expectedBlog = new Blog(afterUpdateTitle, blogId, afterUpdateContents);

    @BeforeEach
    public void clearStore() {
        blogRespository.clear();
    }

    @Test
    @DisplayName("블로그 저장 후에 조회 가능")
    public void 블로그_저장_테스트() {


        blogRespository.save(blog);

        Blog byBlogId = blogRespository.findByBlogId(blogId).get();
        Assertions.assertEquals(blog, byBlogId);

    }


    @Test
    @DisplayName("블로그 저장후 삭제하면 조회 불가능")
    public void 블로그_삭제_테스트() {

        blogRespository.save(blog);

        blogRespository.delete(blogId);

        assertEquals(blogRespository.getSize(),0);
    }

    @Test
    @DisplayName("블로그 저장 후 변경하면 변경된 contents와 같아진다")
    public void 블로그_변경_테스트() {


        blogRespository.save(blog);
        BlogUpdateForm form = new BlogUpdateForm(afterUpdateTitle, afterUpdateContents);

        blogRespository.update(blogId, form);
        Blog afterChangeBlog = blogRespository.findByBlogId(blogId).get();

        assertEquals(expectedBlog.getContents(), afterChangeBlog.getContents());
    }


}