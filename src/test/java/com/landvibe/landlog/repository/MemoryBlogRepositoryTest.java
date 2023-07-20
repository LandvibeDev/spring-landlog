package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.landvibe.landlog.ErrorMessage.NO_BLOG;
import static com.landvibe.landlog.ErrorMessage.WRONG_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        Blog result = repository.findById(blog.getId()).get();
        assertThat(result).isEqualTo(blog);
    }

    @Test
    void deleteById() {
        //given
        Blog blog = new Blog(1L, "aa", "bb");
        repository.save(blog);

        //when
        repository.deleteById(blog.getId());

        //then
        assertThat(repository.findById(blog.getId())).isEqualTo(Optional.empty());
    }

    @Test
    void deleteById_예외() {
        //given
        Blog blog = new Blog(1L, "aa", "bb");
        repository.save(blog);

        Exception e = assertThrows(Exception.class,
                () -> repository.deleteById(2L));
        assertThat(e.getMessage()).isEqualTo(NO_BLOG);
    }

    @Test
    void findBlogByCreatorId() {

        //given
        Blog blog1 = new Blog(1L, "aa", "bb");
        repository.save(blog1);
        Blog blog2 = new Blog(1L, "bb", "cc");
        repository.save(blog2);

        //when
        List<Blog> blogs = repository.findBlogByCreatorId(blog1.getCreatorId());

        //then
        assertThat(blogs.get(0)).isEqualTo(blog1);
        assertThat(blogs.get(1)).isEqualTo(blog2);
    }



}