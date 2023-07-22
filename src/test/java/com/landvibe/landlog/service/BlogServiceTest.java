package com.landvibe.landlog.service;

import ch.qos.logback.core.testUtil.MockInitialContext;
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
import static org.assertj.core.api.Assertions.*;
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

    @DisplayName("creatorId로 블로그 리스트찾기")
    @Test
    void findByCreatorId() {
        Mockito.when(repository.findByCreatorId(1L))
                .thenReturn(blogs);

        assertThat(blogs).isEqualTo(blogService.findBlogsByCreatorId(1L));
    }

    @DisplayName("Id_검색_테스트 성공")
    @Test
    void findById_success() {
        Mockito.when(repository.findByBlogId(1L))
                .thenReturn(Optional.ofNullable(blog1));

        assertThat(blogService.findById(1L)).isEqualTo(blog1);
    }

    @DisplayName("Id_검색_테스트_예외")
    @Test
    void findById_fail() {
        Mockito.when(repository.findByBlogId(2L))
                .thenThrow(new IllegalArgumentException(NO_BLOG.message));

        Exception e = assertThrows(Exception.class,
                () -> blogService.findById(2L));
        assertThat(e.getMessage()).isEqualTo(NO_BLOG.message);
    }

    @DisplayName("블로그_업데이트 테스트")
    @Test
    void updateBlog_success() {
        Mockito.when(repository.findByBlogId(1L))
                .thenReturn(Optional.of(blog1));
        BlogUpdateForm blogUpdateForm = new BlogUpdateForm(1L, "bb", "cc");

        blogService.updateBlog(blogUpdateForm, 1L);
        assertThat(blog2.getTitle()).isEqualTo(blogUpdateForm.getTitle());
    }

    @DisplayName("블로그_업데이트 예외 테스트")
    @Test
    void updateBlog_fail() {
        Mockito.when(repository.findByBlogId(3L))
                .thenThrow(new IllegalArgumentException(NO_BLOG.message));
        BlogUpdateForm blogUpdateForm = new BlogUpdateForm(3L, "bb", "cc");

        Exception e = assertThrows(Exception.class,
                () -> blogService.updateBlog(blogUpdateForm, 1L));
        assertThat(e.getMessage()).isEqualTo(NO_BLOG.message);
    }

    @DisplayName("블로그 삭제 성공")
    @Test
    void deleteBlog_success() {
        Mockito.when(memberService.isValidCreatorId(1L))
                        .thenReturn(true);
        Mockito.when(repository.findByBlogId(2L))
                .thenThrow(new IllegalArgumentException(NO_BLOG.message));

        blogService.deleteBlog(2L, 1L);

        Exception e = assertThrows(Exception.class,
                () -> blogService.findById(2L));
        assertThat(e.getMessage()).isEqualTo(NO_BLOG.message);
    }

    @DisplayName("블로그_삭제_예외")
    @Test
    void deleteBlog_fail() {
        Mockito.when(memberService.isValidCreatorId(2L))
                .thenThrow(new IllegalArgumentException(NO_BLOG.message));

        Exception e = assertThrows(Exception.class,
                () -> blogService.deleteBlog(2L, 2L));
        assertThat(e.getMessage()).isEqualTo(NO_BLOG.message);
    }
}