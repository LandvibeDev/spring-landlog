package com.landvibe.landlog.service;

import com.landvibe.landlog.ErrorMessage;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.dto.BlogUpdateForm;
import com.landvibe.landlog.repository.MemoryBlogRepository;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.landvibe.landlog.ErrorMessage.NO_BLOG;
import static com.landvibe.landlog.ErrorMessage.WRONG_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

    @InjectMocks
    BlogService blogService;
    @Mock
    MemoryBlogRepository repository = new MemoryBlogRepository();

    @BeforeEach
    public void beforeEach() {
        blogService = new BlogService(repository);
    }

    @Test
    void CreatorId_검색_테스트() {
        //given
        Blog blog1 = new Blog(1L, "aa", "bb");
        Blog blog2 = new Blog(1L, "aa", "bb");
        List<Blog> blogs = new ArrayList<>();
        blogs.add(blog1);
        blogs.add(blog2);
        Mockito.when(repository.findByCreatorId(1L))
                .thenReturn(blogs);

        //when
        blogService.registerBlog(blog1);
        blogService.registerBlog(blog2);

        //then
        Assertions.assertThat(blogs).isEqualTo(blogService.findBlogsByCreatorId(1L));
    }

    @Test
    void 업데이트_테스트() {
        //given
        Blog blog = new Blog(1L,1L, "aa", "bb");
        blogService.registerBlog(blog);
        Mockito.when(repository.findByCreatorIdAndBlogId(1L, 1L))
                .thenReturn(Optional.of(blog));
        BlogUpdateForm blogUpdateForm = new BlogUpdateForm(1L, "bb", "cc");

        //when
        blogService.updateBlog(blogUpdateForm, 1L);

        //then
        Assertions.assertThat(blog.getTitle()).isEqualTo(blogUpdateForm.getTitle());
        Assertions.assertThat(blog.getContents()).isEqualTo(blogUpdateForm.getContents());
    }

    @Test
    void findById_예외() {
        Mockito.when(repository.findByBlogId(2L))
                .thenThrow(new IllegalArgumentException(NO_BLOG.message));


        Exception e = assertThrows(Exception.class,
                () -> blogService.findById(2L));
        assertThat(e.getMessage()).isEqualTo(NO_BLOG.message);

    }

    @Test
    void 블로그_삭제_예외() {
        Mockito.when(repository.findByCreatorIdAndBlogId(2L, 2L))
                .thenThrow(new IllegalArgumentException(ErrorMessage.NO_BLOG.message));

        Exception e = assertThrows(Exception.class,
                () -> blogService.deleteBlog(2L, 2L));
        assertThat(e.getMessage()).isEqualTo(NO_BLOG.message);
    }
}