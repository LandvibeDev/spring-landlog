package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
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

    String title = "blogTitle";
    String contents = "blogContents";
    String updatedTitle = "updatedTitle";
    String updatedContents = "updatedContents";

    Long validCreatorId = 1L;
    Long validBlogId = 1L;

    BlogForm blogForm;
    Blog blog;
    Blog updateBlog;
    Member member;

    @BeforeEach
    void before(){
        blogForm = new BlogForm(title, contents);
        blog = new Blog(validCreatorId, blogForm.getTitle(), blogForm.getContents());
        updateBlog = new Blog(validCreatorId, updatedTitle, updatedContents);
        blog.setId(validBlogId);
        updateBlog.setId(validBlogId);
        member = new Member("양재승","jaeseung@inha.kr","1234");
    }

    @AfterEach
    void after(){
        blogRepository.clearStore();
        memberRepository.clearStore();
    }

    @Test
    @DisplayName("게시물 등록 성공")
    void write_success() {
        when(blogRepository.save(any(Blog.class))).thenReturn(any(Long.class));

        memberService.join(member);

        assertEquals(validBlogId, blogService.register(validCreatorId, blogForm)+1);
        verify(blogRepository).save(any(Blog.class));
    }

    @Test
    @DisplayName("게시물 틍록 실패 : 유효하지 않은 creatorId")
    void registerException() {
        BlogForm examForm = new BlogForm(blog.getTitle(), blog.getContents());

        assertThrows(IllegalArgumentException.class, () -> {
            blogService.register(0L, examForm);
        });
    }

    @Test
    @DisplayName("게시물 등록 실패 : 유효하지 않은 게시물")
    void registerException2() {
        BlogForm examForm = new BlogForm("", "");

        assertThrows(IllegalArgumentException.class, () -> {
            blogService.register(1L, examForm);
        });
    }

    @Test
    @DisplayName("게시물 수정 성공")
    void updateBlog() {
        when(blogRepository.findBlogByBlogId(any(Long.class))).thenReturn(Optional.ofNullable(updateBlog));
        when(blogRepository.update(any(Long.class),any(BlogForm.class))).thenReturn(validBlogId);

        Long updateBlogId = blogService.updateBlog(validBlogId, blogForm);
        Blog targetBlog = blogService.findBlogByBlogId(validBlogId);

        assertEquals(validBlogId,updateBlogId);
        assertEquals(updatedTitle,targetBlog.getTitle());
        assertEquals(updatedContents,targetBlog.getContents());
        verify(blogRepository).update(any(Long.class), any(BlogForm.class));
    }

    @Test
    @DisplayName("게시물 삭제 성공")
    void deleteBlog() {
        when(blogRepository.delete(validBlogId)).thenReturn(true);
        when(blogRepository.findBlogByBlogId(validBlogId)).thenReturn(Optional.ofNullable(blog));

        blogService.deleteBlog(validBlogId);

        verify(blogRepository).delete(validBlogId);
    }
}