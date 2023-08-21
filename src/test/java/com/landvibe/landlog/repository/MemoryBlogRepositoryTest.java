package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemoryBlogRepositoryTest {

    @Autowired
    BlogRepository repository;
    Blog blog1 = new Blog(1L, "aa", "bb");
    Blog blog2 = new Blog(1L, "bb", "cc");

    @BeforeEach
    void clear() {
        repository.clear();
    }

    @Test
    void save() {

        //when
        repository.save(blog1);

        //then
        Blog result = repository.findById(blog1.getId()).get();
        assertThat(result.getId()).isEqualTo(blog1.getId());
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
        assertThat(blogs.get(0).getId()).isEqualTo(blog1.getId());
        assertThat(blogs.get(1).getId()).isEqualTo(blog2.getId());
    }
}