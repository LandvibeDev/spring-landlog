package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.form.BlogForm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemoryBlogRepositoryTest {
    MemoryBlogRepository memoryBlogRepository = new MemoryBlogRepository();

    Long creatorId = 1L;
    Long blogId = 1L;
    String title = "testTitle";
    String contents = "testContents";

    BlogForm testBlogForm;
    Blog testBlog;

    @BeforeEach
    public void beforeEach(){
        this.testBlogForm = new BlogForm(title, contents);
        this.testBlog = new Blog(blogId, creatorId, title, contents);
        memoryBlogRepository.save(creatorId,testBlogForm);
    }

    @AfterEach
    public void afterEach() {
        memoryBlogRepository.clearStore();
    }

    @Test
    void save() {
        Blog result = memoryBlogRepository.findBlogByBlogId(blogId).get();
        assertEquals(testBlog.getId(), result.getId());
    }

    @Test
    void update() {
        String updatedTitle = "updated title";
        String updatedContents = "updated contents";

        BlogForm updatedBlogForm = new BlogForm(updatedTitle, updatedContents);

        memoryBlogRepository.update(testBlog.getId(),updatedBlogForm);

        Blog result = memoryBlogRepository.findBlogByCreatorIdAndBlogId(testBlog.getCreatorId(),testBlog.getId()).get();

        assertEquals(result.getTitle(), updatedTitle);
        assertEquals(result.getContents(), updatedContents);
    }

    @Test
    void delete() {
        memoryBlogRepository.delete(testBlog.getId());

        Optional<Blog> result = memoryBlogRepository.findBlogByCreatorIdAndBlogId(testBlog.getCreatorId(),testBlog.getId());
        assertEquals(result.isEmpty(), true);
    }

    @Test
    void findAllBlogsByCreatorId(){
        BlogForm testBlogForm2 = new BlogForm(title, contents);
        BlogForm testBlogForm3 = new BlogForm(title, contents);
        memoryBlogRepository.save(creatorId, testBlogForm2);
        memoryBlogRepository.save(creatorId, testBlogForm3);

        List<Blog> result = memoryBlogRepository.findAllBlogsByCreatorId(creatorId);

        Assertions.assertThat(result.size()).isEqualTo(3);
    }
}