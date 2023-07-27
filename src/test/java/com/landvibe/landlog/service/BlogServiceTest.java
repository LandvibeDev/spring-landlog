package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.repository.MemoryBlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.landvibe.landlog.ErrorMessage.NO_BLOG;
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
    Blog blog3 = new Blog(1L,1L, "bb", "cc");
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
        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(blog1));

        assertThat(blogService.findById(1L, 1L)).isEqualTo(blog1);
    }

    @DisplayName("Id_검색_테스트_예외")
    @Test
    void findById_fail() {
        Mockito.when(repository.findById(2L))
                .thenThrow(new IllegalArgumentException(NO_BLOG.message));

        Exception e = assertThrows(Exception.class,
                () -> blogService.findById(1L,2L));
        assertThat(e.getMessage()).isEqualTo(NO_BLOG.message);
    }

    @DisplayName("블로그_업데이트 테스트")
    @Test
    void updateBlog_success() {
        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(blog1));
        Mockito.when(repository.update(1L, blog1))
                        .thenReturn(blog3);

        assertThat(blogService.updateBlog(1L, 1L, blog1).getTitle()).isEqualTo(blog3.getTitle());
    }

    @DisplayName("블로그_업데이트 예외 테스트")
    @Test
    void updateBlog_fail() {
        Mockito.when(repository.findById(3L))
                .thenThrow(new IllegalArgumentException(NO_BLOG.message));

        Exception e = assertThrows(Exception.class,
                () -> blogService.updateBlog(1L, 3L, blog1));
        assertThat(e.getMessage()).isEqualTo(NO_BLOG.message);
    }

    @DisplayName("블로그 삭제 성공")
    @Test
    void deleteBlog_success() {
        Mockito.when(memberService.isValidCreatorId(1L))
                        .thenReturn(true);
        Mockito.when(repository.findById(2L))
                .thenThrow(new IllegalArgumentException(NO_BLOG.message));

        blogService.deleteBlog(1L, 2L);

        Exception e = assertThrows(Exception.class,
                () -> blogService.findById(1L, 2L));
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