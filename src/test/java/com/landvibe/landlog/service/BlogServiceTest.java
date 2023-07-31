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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {
    @InjectMocks
    BlogService blogService;
    @Mock
    MemoryBlogRepository blogRepository;

    Blog blog1 = Blog.builder()
            .creatorId(1L)
            .id(1L)
            .title("title1")
            .contents("content1")
            .build();
    Blog blog2 = Blog.builder()
            .creatorId(2L)
            .id(1L)
            .title("title2")
            .contents("content1")
            .build();

    List<Blog> blogs = Arrays.asList(blog1,blog2);

    @BeforeEach
    public void beforeEach() {
        blogService = new BlogService(blogRepository);
    }


    @Test
    @DisplayName("블로그 리스트 찾기 테스트")
    void findBlogs_whenGivenCreatorId_ReturnArrays() {
        //given
        Mockito.when(blogRepository.findAllByCreatorId(1L))
                .thenReturn(blogs);


        //when

        //then
        assertThat(blogs).isEqualTo(blogService.findBlogs(1L));
    }


    @Test
    @DisplayName("블로그 한개 찾기 테스트")
    void findOne_WhenGivenBLogIdAndCreatorId_ReturnBlog() {
        //given
        Mockito.when(blogRepository.findOneByBlogIdAndCreatorId(1L,1L))
                .thenReturn(blog1);

        //when

        //then
        assertThat(blog1).isEqualTo(blogService.findOne(1L,1L));

    }



    @Test
    @DisplayName("블로그 삭제 성공 테스트")
    void delete_WhenGivenBlogIdAndCreatorID_ThenReturnTrue() {
        Mockito.when(blogRepository.deleteBlog(1L,1L))
                .thenReturn(true);


        assertThat(blogService.delete(1L,1L)).isEqualTo(true);
    }
    @Test
    @DisplayName("블로그 삭제 실패 테스트")
    void delete_WhenGivenBlogIdAndCreatorID_ThenThrowException() {
        Mockito.when(blogRepository.deleteBlog(1L,2L))
                .thenThrow(new IllegalArgumentException("해당 블로그가 존재하지 않습니다."));


        Exception e = assertThrows(Exception.class,
                () -> blogRepository.deleteBlog(1L,2L));
        assertThat(e.getMessage()).isEqualTo("해당 블로그가 존재하지 않습니다.");
    }

    private List<Blog> blogSetting(Blog blog1, Blog blog2) {
        blogService.create(blog1);
        blogService.create(blog2);

        List<Blog> blogs = new ArrayList<>();
        blogs.add(blog1);
        blogs.add(blog2);
        return blogs;
    }
}
