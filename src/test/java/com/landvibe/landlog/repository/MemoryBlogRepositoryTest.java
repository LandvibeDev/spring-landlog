package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class MemoryBlogRepositoryTest {

    MemoryBlogRepository repository = new MemoryBlogRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }


    @Test
    void save() {
        //given
        Blog blog = new Blog(1L, "aa", "bb");

        //when
        repository.save(blog);

        //then
        Blog result = repository.findByBlogId(blog.getId()).get();
        assertThat(result).isEqualTo(blog);
    }

    @Test
    void deleteById() {
        //given
        Blog blog = new Blog(1L, "aa", "bb");
        repository.save(blog);

        //when
        repository.delete(blog.getId());

        //then
        assertThat(repository.findByBlogId(blog.getId())).isEqualTo(Optional.empty());
    }

    @Test
    void findBlogByCreatorId() {

        //given
        Blog blog1 = new Blog(1L, "aa", "bb");
        repository.save(blog1);
        Blog blog2 = new Blog(1L, "bb", "cc");
        repository.save(blog2);

        //when
        List<Blog> blogs = repository.findByCreatorId(blog1.getCreatorId());

        //then
        assertThat(blogs.get(0)).isEqualTo(blog1);
        assertThat(blogs.get(1)).isEqualTo(blog2);
    }
}