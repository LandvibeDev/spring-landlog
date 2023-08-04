package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.BlogForm;
import com.landvibe.landlog.repository.MemoryBlogRepository;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {
    @InjectMocks
    BlogService blogService;

    @Mock
    MemoryBlogRepository blogRepository;
    @Mock
    MemoryMemberRepository memberRepository;
    @Mock
    MemberService memberService;

    String testTitle = "blogTitle";
    String testContents = "blogContents";
    String updatedTitle = "updatedTitle";
    String updatedContents = "updatedContents";

    Long validCreatorId = 1L;
    Long validBlogId = 1L;

    BlogForm testBlogForm;
    Blog testBlog;
    Blog updateBlog;

    @BeforeEach
    void beforeEach(){
        testBlogForm = new BlogForm(testTitle, testContents);
        testBlog = new Blog(validCreatorId, testTitle, testContents);
        updateBlog = new Blog(validCreatorId, updatedTitle, updatedContents);
    }

    @AfterEach
    void afterEach() {
        blogRepository.clearStore();
        memberRepository.clearStore();
    }

    @Test
    @DisplayName("게시물 등록 성공")
    void register_success() {
        when(blogRepository.save(validCreatorId, testBlogForm)).thenReturn(testBlog);

        assertEquals(testBlog, blogService.register(validCreatorId, testBlogForm));

        verify(blogRepository).save(validCreatorId, testBlogForm);
    }


    @Test
    @DisplayName("게시물 틍록 실패 : 유효하지 않은 creatorId")
    void register_fail_validCreatorId() {
        assertThrows(IllegalArgumentException.class, () -> {
            blogService.register(0L, testBlogForm);
        });
    }

    @Test
    @DisplayName("게시물 등록 실패 : 비어있는 게시물")
    void register_fail_validateBlogForm() {
        BlogForm emptyForm = new BlogForm("", "");

        assertThrows(IllegalArgumentException.class, () -> {
            blogService.register(1L, emptyForm);
        });
    }

    @Test
    @DisplayName("게시물 수정 성공")
    void updateBlog_success() {
        when(blogRepository.update(any(Long.class), any(BlogForm.class))).thenReturn(updateBlog);
        when(blogRepository.findBlogByBlogId(any(Long.class))).thenReturn(Optional.ofNullable(updateBlog));

        BlogForm updateBlogForm = new BlogForm(updatedTitle, updatedContents);
        Blog resultBlog = blogService.update(validBlogId, updateBlogForm);

        assertEquals(updatedTitle, resultBlog.getTitle());
        assertEquals(updatedContents, resultBlog.getContents());

        verify(blogRepository).update(validBlogId, updateBlogForm);
    }

    @Test
    @DisplayName("게시물 수정 실패 : 유효하지 않은 게시물 BlogId")
    void updateBlog_fail_validBlogId(){
        assertThrows(IllegalArgumentException.class, () -> {
            blogService.update(0L, testBlogForm);
        });
    }

    @Test
    @DisplayName("게시물 삭제 성공")
    void deleteBlog_success() {
        when(blogRepository.delete(validBlogId)).thenReturn(true);
        when(blogRepository.findBlogByBlogId(validBlogId)).thenReturn(Optional.ofNullable(testBlog));

        assertEquals(true, blogService.delete(validBlogId));

        verify(blogRepository).delete(validBlogId);
    }
}