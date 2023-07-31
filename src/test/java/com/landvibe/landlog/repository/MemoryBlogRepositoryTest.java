package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemoryBlogRepositoryTest {
    MemoryBlogRepository blogRepository = new MemoryBlogRepository();

    @AfterEach
    public void afterEach() {
        blogRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Blog blog = Blog.builder()
                .title("title")
                .contents("content")
                .id(1L)
                .creatorId(1L)
                .build();

        //when
        blogRepository.save(blog);

        //then
        Blog result = blogRepository.findOneByBlogIdAndCreatorId(blog.getId(), blog.getCreatorId());
        assertThat(result).isEqualTo(blog);
    }

    @Test
    void findAllByCreatorId() {
        //given
        Blog blog1 = Blog.builder().build();
        Blog blog2 = Blog.builder().build();
        setBlog(blog1,blog2);

        blogRepository.save(blog1);
        blogRepository.save(blog2);

        //when
        List<Blog> result = blogRepository.findAllByCreatorId(1L);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void findOneByBlogIdAndCreatorId() {
        //given
        Blog blog1 = Blog.builder().build();
        Blog blog2 = Blog.builder().build();
        setBlog(blog1,blog2);


        blogRepository.save(blog1);
        blogRepository.save(blog2);

        //when
        Blog result = blogRepository.findOneByBlogIdAndCreatorId(1L, 1L);

        //then
        assertThat(result).isEqualTo(blog1);
    }

    @Test
    void deleteBlog() {
        //given
        Blog blog1 = Blog.builder().build();
        Blog blog2 = Blog.builder().build();
        setBlog(blog1,blog2);

        blogRepository.save(blog1);
        blogRepository.save(blog2);

        //when
        blogRepository.deleteBlog(blog1.getId(), blog1.getCreatorId());

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> blogRepository.findOneByBlogIdAndCreatorId(1L, 1L));//예외가 발생해야 한다.

        //then
        assertThat(e.getMessage()).isEqualTo("해당 블로그가 존재하지 않습니다.");
    }
    void setBlog(Blog blog1, Blog blog2) {
        blog1 = Blog.builder()
                .creatorId(1L)
                .id(1L)
                .title("title1")
                .contents("content1")
                .build();

        blog1 = Blog.builder()
                .creatorId(1L)
                .id(2L)
                .title("title2")
                .contents("content2")
                .build();
    }
}
