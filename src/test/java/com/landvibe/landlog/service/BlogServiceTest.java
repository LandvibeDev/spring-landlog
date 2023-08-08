package com.landvibe.landlog.service;

import com.landvibe.landlog.Message;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.form.BlogForm;
import com.landvibe.landlog.form.BlogUpdateForm;
import com.landvibe.landlog.repository.MemoryBlogRepository;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {
    @InjectMocks
    BlogService blogService;

    @Mock
    MemoryBlogRepository blogRepository;

    @Mock
    MemoryMemberRepository memberRepository;

    Long creatorId = 1L;
    Long blogId = 1L;
    Blog blog = new Blog("title", "contents", creatorId);
    Member member = new Member("min", "ab@cd", "pwd");

    @AfterEach
    void afterEach() {
        blogRepository.clearStore();
        memberRepository.clearStore();
    }

    @Test
    void 잘못된_creatorId로_블로그검색() throws Exception {
        when(memberRepository.findById(2L))
                .thenThrow(new IllegalArgumentException(Message.NO_USER.message));
        Exception e = assertThrows(Exception.class,
                () -> blogService.findBlogsByCreatorId(2L));
        assertThat(e.getMessage()).isEqualTo(Message.NO_USER.message);
    }

    @Test
    void 블로그_수정_제목_누락() throws Exception {
        BlogUpdateForm noTitleForm = new BlogUpdateForm(blogId, "", "contents");

        when(memberRepository.findById(creatorId))
                .thenReturn(Optional.of(member));
        when(blogRepository.findBlogByBlogId(blogId))
                .thenReturn(Optional.of(blog));
        Exception e = assertThrows(Exception.class,
                () -> blogService.update(creatorId, noTitleForm));
        assertThat(e.getMessage()).isEqualTo(Message.NO_INPUT_TITLE.message);
    }

    @Test
    void 블로그_수정_내용_누락() throws Exception {
        BlogUpdateForm noContentsForm = new BlogUpdateForm(blogId, "title", "");

        when(memberRepository.findById(creatorId))
                .thenReturn(Optional.of(member));
        when(blogRepository.findBlogByBlogId(blogId))
                .thenReturn(Optional.of(blog));
        Exception e = assertThrows(Exception.class,
                () -> blogService.update(creatorId, noContentsForm));
        assertThat(e.getMessage()).isEqualTo(Message.NO_INPUT_CONTENTS.message);
    }


    @Test
    void 블로그_수정_블로그id_없음() throws Exception {
        BlogUpdateForm noContentsForm = new BlogUpdateForm(blogId, "title", "");

        when(memberRepository.findById(creatorId))
                .thenReturn(Optional.of(member));
        Exception e = assertThrows(Exception.class,
                () -> blogService.update(creatorId, noContentsForm));
        assertThat(e.getMessage()).isEqualTo(Message.NO_BLOG.message);
    }

    @Test
    void 블로그_추가_제목_누락() throws Exception {
        BlogForm noTitleForm = new BlogForm("", "contents");

        when(memberRepository.findById(creatorId))
                .thenReturn(Optional.of(member));
        Exception e = assertThrows(Exception.class,
                () -> blogService.create(creatorId, noTitleForm));
        assertThat(e.getMessage()).isEqualTo(Message.NO_INPUT_TITLE.message);
    }

    @Test
    void 블로그_추가_내용_누락() throws Exception {
        BlogForm noContentsForm = new BlogForm("title", "");

        when(memberRepository.findById(creatorId))
                .thenReturn(Optional.of(member));
        Exception e = assertThrows(Exception.class,
                () -> blogService.create(creatorId, noContentsForm));
        assertThat(e.getMessage()).isEqualTo(Message.NO_INPUT_CONTENTS.message);
    }

    @Test
    void 블로그_삭제_잘못된_creatorId() throws Exception {
        when(memberRepository.findById(2L))
                .thenThrow(new IllegalArgumentException(Message.NO_USER.message));
        Exception e = assertThrows(Exception.class,
                () -> blogService.delete(2L, blogId));
        assertThat(e.getMessage()).isEqualTo(Message.NO_USER.message);
    }
}
