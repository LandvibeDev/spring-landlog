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

    @Test
    @DisplayName("[creatorId 유효성검정 성공]")
    void validateCreatorId_success() {
        long creatorId = 1L;
        Member member = new Member("name", "email", "password");
        member.setId(creatorId);

        Mockito.when(memberRepository.findById(creatorId))
                .thenReturn(Optional.of(member));

        assertDoesNotThrow(() -> {
            blogService.validateCreatorId(creatorId);
        });
    }

    @Test
    @DisplayName("[creatorId 유효성검정 실패] 존재하지 않는 멤버")
    void validateCreatorId_fail_empty_Optional() {
        long creatorId = 1L;

        Mockito.when(memberRepository.findById(creatorId))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> {
                    blogService.validateCreatorId(creatorId);
                });
        assertThat(exception.getMessage()).isEqualTo(NO_MATCH_MEMBER_WITH_CREATOR_ID.message);
    }

    @Test
    @DisplayName("[creatorId 유효성검정 실패] empty creatorId")
    void validateCreatorId_fail_null_or_zero() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> {
                    blogService.validateCreatorId(null);
                });
        assertThat(exception.getMessage()).isEqualTo(EMPTY_CREATOR_ID.message);
    }

    @Test
    @DisplayName("[blogId 유효성검정 성공]")
    void validateBlogId_success() {
        long blogId = 1L;
        Blog blog = new Blog(1L, "title", "contents");
        blog.setId(blogId);

        Mockito.when(blogRepository.findById(blogId))
                .thenReturn(Optional.of(blog));

        assertDoesNotThrow(() -> {
            blogService.validateBlogId(blogId);
        });
    }

    @Test
    @DisplayName("[blogId 유효성검정 실패] 존재하지 않는 게시글")
    void validateBlogId_fail_empty_Optional() {
        long blogId = 1L;

        Mockito.when(blogRepository.findById(blogId))
                .thenReturn(Optional.empty());

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
    @DisplayName("[게시글] 등록 테스트")
    void write() {
        //
        Blog blog = new Blog(1L, "title", "contents");
        //
        blogService.write(blog);
        //
        Mockito.verify(blogRepository)
                .save(Mockito.any(Blog.class));
        Mockito.verify(blogRepository, Mockito.times(1))
                .save(Mockito.any(Blog.class));
    }

    @Test
    @DisplayName("[게시글] 수정 테스트")
    void update() {
        //
        blogRepository.save(new Blog(1L, "title", "contents"));
        Blog updateBlog = new Blog(1L, "updateTitle", "updateContents");
        //
        blogService.update(updateBlog);
        //
        Mockito.verify(blogRepository)
                .update(Mockito.any(Blog.class));
        Mockito.verify(blogRepository, Mockito.times(1))
                .update(Mockito.any(Blog.class));
    }

    @Test
    @DisplayName("[게시글] 삭제 테스트")
    void delete() {
        //
        blogRepository.save(new Blog(1L, "title", "contents"));
        //
        blogService.deleteById(1L);
        //
        Mockito.verify(blogRepository)
                .deleteById(Mockito.any(Long.class));
        Mockito.verify(blogRepository, Mockito.times(1))
                .deleteById(Mockito.any(Long.class));
    }

    @Test
    @DisplayName("[게시글] creatorId 기반으로 찾기")
    void findBlogsByCreatorId() {
        //
        blogRepository.save(new Blog(1L, "title", "contents"));
        //
        blogService.findBlogsByCreatorId(1L);
        //
        Mockito.verify(blogRepository)
                .findBlogsByCreatorId(Mockito.any(Long.class));
        Mockito.verify(blogRepository, Mockito.times(1))
                .findBlogsByCreatorId(Mockito.any(Long.class));
    }
}