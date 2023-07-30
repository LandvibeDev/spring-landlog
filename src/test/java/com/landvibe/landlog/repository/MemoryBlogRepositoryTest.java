package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryBlogRepositoryTest {

    MemoryBlogRepository memoryBlogRepository;

    String title = "title";
    String contents = "contents";
    Long memberId = 1L;

    String updateTitle = "modified_title";
    String updateContents = "modified_contents";

    @BeforeEach
    void setUp() {
        memoryBlogRepository = new MemoryBlogRepository();
    }

    @AfterEach
    void tearDown() {
        memoryBlogRepository.clearStore();
    }

    @Test
    @DisplayName("save_정상인풋_정상수행")
    void save() {
        //when
        Long blogId = memoryBlogRepository.save(memberId, title, contents);
        Blog actualBlog = memoryBlogRepository.findById(blogId).get();

        //then
        assertThat(blogId).isEqualTo(actualBlog.getId());
    }

    @Test
    @DisplayName("findById_정상수행")
    void findById() {
        //given
        Long blogId = memoryBlogRepository.save(memberId, title, contents);

        //when
        Blog blog = memoryBlogRepository.findById(blogId).get();

        //then
        assertThat(blog.getId()).isEqualTo(blogId);
    }

    @Test
    @DisplayName("memberId로_blogs_조회_정상수행")
    void findByMemberId() {
        //given
        Long blogId = memoryBlogRepository.save(memberId, title, contents);

        //when
        List<Blog> blogs = memoryBlogRepository.findByCreatorId(memberId);
        int expectedBlogsSize = 1;

        //then
        assertThat(blogs.size()).isEqualTo(expectedBlogsSize);
    }

    @Test
    @DisplayName("blog_수정_정상수행")
    void modify() {
        //given
        Long blogId = memoryBlogRepository.save(memberId, title, contents);

        //when
        memoryBlogRepository.modify(blogId, updateTitle, updateContents, memberId);
        Blog actual = memoryBlogRepository.findById(blogId).get();

        //then
        assertThat(actual.getTitle()).isEqualTo(updateTitle);
        assertThat(actual.getContents()).isEqualTo(updateContents);
        assertThat(actual.getId()).isEqualTo(blogId);
        assertThat(actual.getCreatorId()).isEqualTo(memberId);
    }

    @Test
    void erase() {
        //given
        Long blogId = memoryBlogRepository.save(memberId, title, contents);

        //when
        memoryBlogRepository.erase(blogId);
        Optional<Blog> actual = memoryBlogRepository.findById(blogId);

        //then
        assertThat(actual).isEqualTo(Optional.empty());
    }
}