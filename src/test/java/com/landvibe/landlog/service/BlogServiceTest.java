package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.repository.MemoryBlogRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class BlogServiceTest {

    BlogService blogService;
    MemoryBlogRepository blogRepository;

    @BeforeEach
    public void beforeEach() {
        blogRepository = new MemoryBlogRepository();
        blogService = new BlogService(blogRepository);
    }

    @AfterEach
    public void afterEach() {
        blogRepository.clearStore();
    }

    @Test
    void findBlog() {
        // given
        Blog blog1 = new Blog(1L, "t1", "c1", 1L);
        Blog blog2 = new Blog(2L, "t2", "c2", 1L);
        blogService.createBlog(blog1);
        Blog expected = blogService.createBlog(blog2);

        // when
        Blog actual = blogService.findBlog(expected.getId()).get();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findBlogsByCreator() {
        // given
        long user1 = 1L;
        long user2 = 2L;

        Blog blog1 = new Blog(1L, "t1", "c1", user1);
        Blog blog2 = new Blog(2L, "t2", "c2", user1);
        Blog blog3 = new Blog(3L, "t3", "c3", user1);
        Blog blog4 = new Blog(4L, "t4", "c4", user2);
        Blog blog5 = new Blog(5L, "t5", "c5", user2);
        blogService.createBlog(blog1);
        blogService.createBlog(blog2);
        blogService.createBlog(blog3);
        blogService.createBlog(blog4);
        blogService.createBlog(blog5);

        List<Blog> expected = new ArrayList<>();
        expected.add(blog1);
        expected.add(blog2);
        expected.add(blog3);

        // when
        List<Blog> actual = blogService.findBlogsByCreator(user1);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void createBlog() {
        // given
        Blog expected = new Blog(1L, "t1", "c1", 1L);

        // when
        Blog actual = blogService.createBlog(expected);
        expected.setId(actual.getId());

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void updateBlog() {
        // given
        Blog actual = blogService.createBlog(new Blog(1L, "t1", "c1", 1L));
        actual.setTitle("t2");
        actual.setContents("c2");

        // when
        Blog expected = blogService.updateBlog(actual);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deleteBlog() {
        // given
        Blog blog = blogService.createBlog(new Blog(1L, "t1", "c1", 1L));

        // when
        blogService.deleteBlog(blog.getId());

        // then
        Optional<Blog> actual = blogService.findBlog(blog.getId());
        assertThat(actual.isPresent()).isEqualTo(false);
    }
}