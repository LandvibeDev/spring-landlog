package com.landvibe.landlog.service;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void 등록() {
        //given
        Blog blog = new Blog(1L, "aa", "bb");
        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.of(blog));

        //when
        blogService.registerBlog(blog);

        //then
        Assertions.assertThat(blogService.findById(1L)).isEqualTo(blog);
    }

    @Test
    void findBlogsByCreatorId() {
        //given
        Blog blog1 = new Blog(1L, "aa", "bb");
        Blog blog2 = new Blog(1L, "aa", "bb");
        List<Blog> blogs = new ArrayList<>();
        blogs.add(blog1);
        blogs.add(blog2);
        Mockito.when(repository.findBlogByCreatorId(1L))
                .thenReturn(blogs);

        //when
        blogService.registerBlog(blog1);
        blogService.registerBlog(blog2);

        //then
        Assertions.assertThat(blogs).isEqualTo(blogService.findBlogsByCreatorId(1L));
    }

    @Test
    void updateBlog() {
        //given
        Blog blog = new Blog(1L, "aa", "bb");
        blogService.registerBlog(blog);
        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.of(blog));
        BlogUpdateForm blogUpdateForm = new BlogUpdateForm(1L, "bb", "cc");

        //when
        blogService.updateBlog(blogUpdateForm);

        //then
        Assertions.assertThat(blog.getTitle()).isEqualTo(blogUpdateForm.getTitle());
        Assertions.assertThat(blog.getContents()).isEqualTo(blogUpdateForm.getContents());
    }
}