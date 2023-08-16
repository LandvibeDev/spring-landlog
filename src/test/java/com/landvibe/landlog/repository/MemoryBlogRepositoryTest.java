package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryBlogRepositoryTest {
    MemoryBlogRepository repository = new MemoryBlogRepository();
    Long creatorId = 1L;
    Long blogId = 1L;
    Blog blog = new Blog("title1","contents1",creatorId);

    @BeforeEach
    public void beforeEach() {
        repository.save(blog);
    }

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    @DisplayName("블로그 저장")
    void save() {
        //when
        Blog result = repository.findBlogByBlogId(blog.getId()).get();

        //then
        assertThat(result).isEqualTo(blog);
    }

    @Test
    @DisplayName("creatorId로 블로그 찾기")
    void findBlogsByCreatorId() {
        //given
        Blog anotherBlog = new Blog("title2","contents2", creatorId);
        repository.save(anotherBlog);

        //when
        List<Blog> result = repository.findBlogsByCreatorId(creatorId);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("블로그 수정")
    void update() {
        //given
        String updateTitle = "updateTitle";
        String updateContents = "updateContents";

        //when
        blog.setTitle(updateTitle);
        blog.setContents(updateContents);
        blog.setId(blogId);
        repository.update(blog);
        Blog updatedBlog = repository.findBlogByBlogId(blogId).get();

        //then
        assertThat(updatedBlog.getTitle()).isEqualTo(updateTitle);
        assertThat(updatedBlog.getContents()).isEqualTo(updateContents);
    }

    @Test
    @DisplayName("블로그 삭제")
    void delete() {
        //when
        repository.delete(blog);

        //then
        assertThat(repository.findBlogByBlogId(blogId).isEmpty()).isEqualTo(true);
    }
}
