package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogForm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemoryBlogRepositoryTest {
    MemoryBlogRepository memoryBlogRepository = new MemoryBlogRepository();

    Long creatorId = 1L;
    Long blogId = 1L;
    String title = "제목";
    String contents = "내용";

    @AfterEach
    public void afterEach() {
        memoryBlogRepository.clearStore();
    }

    @Test
    void save() {
        Blog blog = new Blog(creatorId, title, contents);

        memoryBlogRepository.save(blog);

        Blog result = memoryBlogRepository.findBlogByCreatorIdAndBlogId(blog.getCreatorId(),blog.getId()).get();
        assertEquals(blog.getId(), result.getId());
    }

    @Test
    void update() {
        String updatedTitle = "updated title";
        String updatedContents = "updated contents";

        Blog blog = new Blog(creatorId, title, contents);
        BlogForm updatedBlogForm = new BlogForm(updatedTitle, updatedContents);

        memoryBlogRepository.save(blog);
        memoryBlogRepository.update(blog.getId(),updatedBlogForm);

        Blog result = memoryBlogRepository.findBlogByCreatorIdAndBlogId(blog.getCreatorId(),blog.getId()).get();

        assertEquals(result.getTitle(), updatedTitle);
        assertEquals(result.getContents(), updatedContents);
    }

    @Test
    void delete() {
        Blog blog = new Blog(creatorId, title, contents);
        memoryBlogRepository.save(blog);

        memoryBlogRepository.delete(blog.getId());

        Optional<Blog> result = memoryBlogRepository.findBlogByCreatorIdAndBlogId(blog.getCreatorId(),blog.getId());
        assertEquals(result.isEmpty(), true);
    }
}