package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogCreateForm;
import com.landvibe.landlog.form.BlogUpdateForm;
import com.landvibe.landlog.repository.BlogRespository;
import com.landvibe.landlog.repository.MemoryBlogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;


@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {
    String title = "title";
    String content = "content";
    Long creatorId = 1L;


    private BlogService blogService = new BlogService(new MemoryBlogRepository());


    @Test
    @DisplayName("블로그 1개를 저장하면 저장되어있는 블로그의 수는 1개")
    public void 블로그_저장_테스트() {
        BlogCreateForm form = new BlogCreateForm(title, content);
        blogService.createBlog(creatorId, form);
        List<Blog> result = blogService.findAllBlogs(creatorId);
        List<Blog> blogList = result.stream()
                .filter(blog -> creatorId == blog.getCreatorId()).toList();
        Assertions.assertEquals(blogList.size(), 1);
    }

    @Test
    @DisplayName("블로그 1개를 저장하고 삭제를 하면 남아있는 블로그의 갯수는 0개")
    public void 블로그_삭제_테스트() {
        BlogCreateForm form = new BlogCreateForm(title, content);
        blogService.createBlog(creatorId, form);
        blogService.delete(1L);

        List<Blog> result = blogService.findAllBlogs(creatorId);
        List<Blog> blogList = result.stream()
                .filter(blog -> creatorId == blog.getCreatorId()).toList();
        Assertions.assertEquals(blogList.size(), 0);
    }

    @Test
    @DisplayName("블로그 1개를 저장하고 update를 하면 contents가 변경")
    public void 블로그_변경_테스트() {
        BlogCreateForm form = new BlogCreateForm(title, content);
        blogService.createBlog(creatorId, form);

        BlogUpdateForm updateForm = new BlogUpdateForm("after update", "after update");
        blogService.update(1L, updateForm);
        Blog result = blogService.findBlogById(1L);

        Assertions.assertNotEquals(content, result.getContents());
    }


}
