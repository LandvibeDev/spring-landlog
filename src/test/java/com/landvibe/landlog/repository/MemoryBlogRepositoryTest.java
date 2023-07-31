package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class MemoryBlogRepositoryTest {

    MemoryBlogRepository repository = new MemoryBlogRepository();
    Blog blog1 = new Blog(1L, "aa", "bb");
    Blog blog2 = new Blog(1L, "bb", "cc");

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    void save() {

        //when
        repository.save(blog1);

        //then
        Blog result = repository.findById(blog1.getId()).get();
        assertThat(result).isEqualTo(blog1);
    }

    @Test
    void deleteById() {

        //given
        repository.save(blog1);

        //when
        repository.delete(blog1.getId());

        //then
        assertThat(repository.findById(blog1.getId())).isEqualTo(Optional.empty());
    }

    @Test
    void findBlogByCreatorId() {
        //given
        repository.save(blog1);
        repository.save(blog2);

        //when
        List<Blog> blogs = repository.findByCreatorId(blog1.getCreatorId());

        //then
        assertThat(blogs.get(0)).isEqualTo(blog1);
        assertThat(blogs.get(1)).isEqualTo(blog2);
    }
}