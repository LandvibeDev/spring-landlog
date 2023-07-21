package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogCreateForm;
import com.landvibe.landlog.form.BlogUpdateForm;
import com.landvibe.landlog.repository.MemoryBlogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {


    @Mock
    private MemoryBlogRepository blogRepository;

    @InjectMocks
    BlogService blogService;

    String title = "title";
    String content = "content";
    String afterUpdateTitle = "afterUpdateTitle";
    String afterUpdateContent = "afterUpdateContent";
    Long creatorId = 1L;
    Long blogId = 1L;

    BlogCreateForm createForm = new BlogCreateForm(title, content);
    BlogUpdateForm updateForm = new BlogUpdateForm(afterUpdateTitle, afterUpdateContent);


    @Test
    @DisplayName("블로그 생성 테스트")
    public void 블로그_생성_테스트() {
        when(blogRepository.save(any(Blog.class))).thenReturn(1L);

        Blog blog = new Blog(title, creatorId, content);
        blogService.createBlog(creatorId, createForm);

        verify(blogRepository)
                .save(any(Blog.class));
        verify(blogRepository, Mockito.times(1))
                .save(any(Blog.class));
    }

    @Test
    @DisplayName("블로그 삭제 테스트")
    public void 블로그_삭제_테스트() {
        when(blogRepository.delete(blogId)).thenReturn(blogId);

        Blog blog = new Blog(title, creatorId, content);
        blogService.createBlog(creatorId, createForm);

        blogService.delete(blogId);

        verify(blogRepository).delete(blogId);
    }

    @Test
    @DisplayName("블로그 업데이트 테스트")
    public void 블로그_업데이트_테스트() {
        when(blogRepository.update(any(Long.class), any(BlogUpdateForm.class)))
                .thenReturn(1L);

        Blog blog = new Blog(title, creatorId, content);
        blogService.createBlog(creatorId, createForm);

        blogService.update(blogId, updateForm);

        verify(blogRepository)
                .update(any(Long.class), any(BlogUpdateForm.class));
        verify(blogRepository, Mockito.times(1))
                .update(any(Long.class), any(BlogUpdateForm.class));


    }
}
