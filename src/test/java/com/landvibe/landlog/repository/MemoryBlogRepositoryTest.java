package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import org.junit.jupiter.api.AfterEach;
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

    @Test
    void findById() {
        // given
        Blog blog1 = new Blog(1L, "t1", "c1", 1L);
        Blog blog2 = new Blog(2L, "t2", "c2", 1L);
        repository.create(blog1);
        Blog expected = repository.create(blog2);

        // when
        Blog actual = repository.findById(expected.getId()).get();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByCreator() {
        // given
        long user1 = 1L;
        long user2 = 2L;

        Blog blog1 = new Blog(1L, "t1", "c1", user1);
        Blog blog2 = new Blog(2L, "t2", "c2", user1);
        Blog blog3 = new Blog(3L, "t3", "c3", user1);
        Blog blog4 = new Blog(4L, "t4", "c4", user2);
        Blog blog5 = new Blog(5L, "t5", "c5", user2);
        repository.create(blog1);
        repository.create(blog2);
        repository.create(blog3);
        repository.create(blog4);
        repository.create(blog5);

        List<Blog> expected = new ArrayList<>();
        expected.add(blog1);
        expected.add(blog2);
        expected.add(blog3);

        // when
        List<Blog> actual = repository.findByCreator(user1);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void create() {
        // given
        Blog expected = new Blog(1L, "t1", "c1", 1L);

        // when
        Blog actual = repository.create(expected);
        expected.setId(actual.getId());

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void update() {
        // given
        Blog actual = repository.create(new Blog(1L, "t1", "c1", 1L));
        actual.setTitle("t2");
        actual.setContents("c2");

        // when
        Blog expected = repository.update(actual);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void delete() {
        // given
        Blog blog = repository.create(new Blog(1L, "t1", "c1", 1L));

        // when
        repository.delete(blog.getId());

        // then
        Optional<Blog> actual = repository.findById(blog.getId());
        assertThat(actual.isPresent()).isEqualTo(false);
    }
}