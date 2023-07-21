package com.landvibe.landlog.service;

import com.landvibe.landlog.ErrorMessage;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.dto.BlogUpdateForm;
import com.landvibe.landlog.repository.MemoryBlogRepository;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
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
    MemoryBlogRepository repository;
    @Mock
    MemberService memberService;
    Blog blog1 = new Blog(1L,1L, "aa", "bb");
    Blog blog2 = new Blog(2L,1L, "bb", "cc");
    List<Blog> blogs = Arrays.asList(blog1,blog2);


    @BeforeEach
    public void beforeEach() {
        blogService = new BlogService(repository, memberService);
    }

    @Test
    void CreatorId_검색_테스트() {
        Mockito.when(repository.findByCreatorId(1L))
                .thenReturn(blogs);

        Assertions.assertThat(blogs).isEqualTo(blogService.findBlogsByCreatorId(1L));
    }

    @DisplayName("블로그_업데이트 테스트")
    @Test
    void updateBlog() {
        Mockito.when(repository.findByBlogId(1L))
                .thenReturn(Optional.of(blog2));
        BlogUpdateForm blogUpdateForm = new BlogUpdateForm(1L, "bb", "cc");

        blogService.updateBlog(blogUpdateForm, 1L);

        Assertions.assertThat(blog2.getTitle()).isEqualTo(blogUpdateForm.getTitle());
        Assertions.assertThat(blog2.getContents()).isEqualTo(blogUpdateForm.getContents());
    }

    @DisplayName("Id_검색_테스트")
    @Test
    void findById() {
        Mockito.when(repository.findByBlogId(2L))
                .thenThrow(new IllegalArgumentException(NO_BLOG.message));

        Exception e = assertThrows(Exception.class,
                () -> blogService.findById(2L));
        assertThat(e.getMessage()).isEqualTo(NO_BLOG.message);

    }

    @DisplayName("블로그_삭제_예외")
    @Test
    void deleteBlog() {
        Mockito.when(memberService.findById(2L))
                .thenThrow(new IllegalArgumentException(ErrorMessage.NO_BLOG.message));

        Exception e = assertThrows(Exception.class,
                () -> blogService.deleteBlog(2L, 2L));
        assertThat(e.getMessage()).isEqualTo(NO_BLOG.message);
    }
}