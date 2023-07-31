package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryBlogRepository;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.landvibe.landlog.ErrorMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

    @InjectMocks
    BlogService blogService;
    @Mock
    MemoryBlogRepository blogRepository;
    @Mock
    MemoryMemberRepository memberRepository;

    long creatorId = 1L;
    long blogId = 1L;

    private Member createMember() {
        Member member = Member.builder()
                .id(creatorId)
                .name("name")
                .email("email")
                .password("password")
                .build();
        return member;
    }

    private Blog createBlog() {
        Blog blog = Blog.builder()
                .id(blogId)
                .creatorId(creatorId)
                .title("title")
                .contents("contents")
                .build();
        return blog;
    }

    @Test
    @DisplayName("[creatorId 유효성검정 성공]")
    void validateCreatorId_success() {
        Member member = createMember();

        Mockito.when(memberRepository.existsById(creatorId))
                .thenReturn(true);

        assertDoesNotThrow(() -> {
            blogService.validateCreatorId(creatorId);
        });
    }

    @Test
    @DisplayName("[creatorId 유효성검정 실패] 존재하지 않는 멤버")
    void validateCreatorId_fail_empty_Optional() {
        Mockito.when(memberRepository.existsById(creatorId))
                .thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> {
                    blogService.validateCreatorId(creatorId);
                });
        assertThat(exception.getMessage())
                .isEqualTo(NO_MATCH_MEMBER_WITH_CREATOR_ID.message);
    }

    @Test
    @DisplayName("[creatorId 유효성검정 실패] empty creatorId")
    void validateCreatorId_fail_null_or_zero() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> {
                    blogService.validateCreatorId(null);
                });
        assertThat(exception.getMessage())
                .isEqualTo(EMPTY_CREATOR_ID.message);
    }

    @Test
    @DisplayName("[blogId 유효성검정 성공]")
    void validateBlogId_success() {
        Blog blog = createBlog();
        blog.setId(blogId);

        Mockito.when(blogRepository.existsById(blogId))
                .thenReturn(true);

        assertDoesNotThrow(() -> {
            blogService.validateBlogId(blogId);
        });
    }

    @Test
    @DisplayName("[blogId 유효성검정 실패] 존재하지 않는 게시글")
    void validateBlogId_fail_empty_Optional() {
        Mockito.when(blogRepository.existsById(blogId))
                .thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> {
                    blogService.validateBlogId(blogId);
                });
        assertThat(exception.getMessage())
                .isEqualTo(NO_MATCH_BLOG_WITH_BLOG_ID.message);
    }

    @Test
    @DisplayName("[blogId 유효성검정 실패] empty blogId")
    void validateBlogId_fail_null_or_zero() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> {
                    blogService.validateBlogId(null);
                });
        assertThat(exception.getMessage())
                .isEqualTo(EMPTY_BLOG_ID.message);
    }

    @Test
    @DisplayName("[게시글 등록 성공]")
    void write_success() {
        //
        Member member = createMember();
        Blog blog = createBlog();

        Mockito.when(memberRepository.existsById(creatorId))
                .thenReturn(true);
        //
        blogService.write(blog);
        //
        Mockito.verify(blogRepository)
                .save(blog);
    }

    @Test
    @DisplayName("[게시글 등록 실패] creatorId 없음")
    void write_fail_no_creatorId() {
        //
        Blog blog = createBlog();

        Mockito.when(memberRepository.existsById(creatorId))
                .thenReturn(false);
        //
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> {
                    blogService.write(blog);
                });
        assertThat(exception.getMessage())
                .isEqualTo(NO_MATCH_MEMBER_WITH_CREATOR_ID.message);
    }

    @Test
    @DisplayName("[게시글 수정 성공]")
    void update_success() {
        //
        Member member = createMember();
        blogRepository.save(createBlog());
        Blog updateBlog = Blog.builder()
                .creatorId(creatorId)
                .title("updateTitle")
                .contents("updateContents")
                .build();

        Mockito.when(memberRepository.existsById(creatorId))
                .thenReturn(true);
        //
        blogService.update(updateBlog);
        //
        Mockito.verify(blogRepository)
                .update(updateBlog);
    }

    @Test
    @DisplayName("[게시글 수정 실패] creatorId 없음")
    void update_fail_no_creatorId() {
        //
        blogRepository.save(createBlog());
        Blog updateBlog = Blog.builder()
                .creatorId(creatorId)
                .title("updateTitle")
                .contents("updateContents")
                .build();

        Mockito.when(memberRepository.existsById(creatorId))
                .thenReturn(false);
        //
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> {
                    blogService.update(updateBlog);
                });
        assertThat(exception.getMessage())
                .isEqualTo(NO_MATCH_MEMBER_WITH_CREATOR_ID.message);
    }

    @Test
    @DisplayName("[게시글 삭제 성공]")
    void delete_success() {
        //
        Member member = createMember();
        Blog blog = createBlog();

        Mockito.when(memberRepository.existsById(creatorId))
                .thenReturn(true);
        Mockito.when(blogRepository.findById(blogId))
                .thenReturn(Optional.of(blog));
        //
        blogService.deleteById(blogId);
        //
        Mockito.verify(blogRepository)
                .deleteById(blogId);
    }

    @Test
    @DisplayName("[게시글 삭제 실패] creatorId 없음")
    void delete_fail_no_creatorId() {
        //
        Blog blog = createBlog();

        Mockito.when(memberRepository.existsById(creatorId))
                .thenReturn(false);
        Mockito.when(blogRepository.findById(blogId))
                .thenReturn(Optional.of(blog));
        //
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> {
                    blogService.deleteById(blogId);
                });
        assertThat(exception.getMessage())
                .isEqualTo(NO_MATCH_MEMBER_WITH_CREATOR_ID.message);
    }


    @Test
    @DisplayName("[게시글 creatorId 기반으로 찾기 성공]")
    void findBlogsByCreatorId_success() {
        //
        Member member = createMember();

        Mockito.when(memberRepository.existsById(creatorId))
                .thenReturn(true);
        //
        blogService.findBlogsByCreatorId(creatorId);
        //
        Mockito.verify(blogRepository)
                .findBlogsByCreatorId(creatorId);
    }

    @Test
    @DisplayName("[게시글 creatorId 기반으로 찾기 실패] creatorId 없음")
    void findBlogsByCreatorId_fail() {
        //
        Mockito.when(memberRepository.existsById(creatorId))
                .thenReturn(false);
        //
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> {
                    blogService.findBlogsByCreatorId(creatorId);
                });
        assertThat(exception.getMessage())
                .isEqualTo(NO_MATCH_MEMBER_WITH_CREATOR_ID.message);
    }
}