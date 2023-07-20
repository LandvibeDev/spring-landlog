package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryBlogRepositoryTest {

    MemoryBlogRepository repository = new MemoryBlogRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    private Blog createBlog() {
        Blog blog = new Blog(1L, "title", "contents");
        repository.save(blog);
        return blog;
    }

    private List<Blog> createBlogList() {
        List<Blog> blogList = new ArrayList<>();
        Blog blog = new Blog(1L, "title", "contents");
        repository.save(blog);
        blogList.add(blog);
        Blog blog2 = new Blog(1L, "title2", "contents2");
        repository.save(blog2);
        blogList.add(blog2);
        return blogList;
    }

    @Test
    @DisplayName("게시글 등록 테스트")
    void save() {
        //given
        Blog blog = createBlog();

        //when
        repository.save(blog);

        //then
        Blog result = repository.findById(blog.getId()).get();
        assertThat(result).isEqualTo(blog);
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void update() {
        //given
        Blog blog = createBlog();
        Blog updateBlog = new Blog(blog.getId(), 1L, "updateTitle", "updateContents");

        //when
        repository.update(updateBlog);

        //then
        Blog result = repository.findById(1L).get();
        assertThat(result).isEqualTo(updateBlog);
    }

    @Test
    @DisplayName("blogId 기반으로 게시글 찾기")
    void findById() {
        //given
        Blog blog = createBlog();

        //when
        Blog result = repository.findById(blog.getId()).get();

        //then
        assertThat(result).isEqualTo(blog);
    }

    @Test
    @DisplayName("blogId 기반으로 게시글 삭제하기")
    void deleteById() {
        //given
        Blog blog = createBlog();

        //when
        Long deleteId = repository.deleteById(blog.getId());

        //then
        Optional<Blog> byId = repository.findById(blog.getId());
        assertThat(byId).isEmpty();
    }


    @Test
    @DisplayName("creatorId 기반으로 게시글 찾기")
    void findBlogsByCreatorId() {
        //given
        List<Blog> blogList = createBlogList();

        //when
        List<Blog> result = repository.findBlogsByCreatorId(1L);

        //then
        assertThat(result).isEqualTo(blogList);
    }
}