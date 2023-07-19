package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.UpdateBlogForm;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryBlogRepository;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class BlogServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;
    BlogService blogService;
    MemoryBlogRepository blogRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
        blogRepository = new MemoryBlogRepository();
        blogService = new BlogService(blogRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
        blogRepository.clearStore();
    }

    @Test
    @DisplayName("블로그 리스트 찾기 테스트")
    void findBlogs_whenGivenCreatorId_ReturnArrays() {
        //given
        Member member = new Member(1L, "aa", "aa", "aa");
        Blog blog1 = new Blog(1L, 1L, "title1", "contents1");
        Blog blog2 = new Blog(1L, 2L, "title2", "contents2");
        memberService.join(member);

        //when
        List<Blog> blogs = blogSetting(blog1, blog2);

        //then
        assertThat(blogService.findBlogs(1L)).isEqualTo(blogs);
        assertThat(blogService.findBlogs(2L)).isEmpty();
    }


    @Test
    @DisplayName("블로그 한개 찾기 테스트")
    void findOne_WhenGivenBLogIdAndCreatorId_ReturnBlog() {
        //given
        Member member = new Member(1L, "aa", "aa", "aa");
        Blog blog1 = new Blog(1L, 1L, "title1", "contents1");
        Blog blog2 = new Blog(1L, 2L, "title2", "contents2");
        memberService.join(member);

        //when
        List<Blog> blogs = blogSetting(blog1, blog2);

        //then
        assertThat(blogService.findOne(1L, 1L)).isEqualTo(blog1);
        assertThat(blogService.findOne(2L, 1L)).isEqualTo(blog2);

        assertThat(blogService.findOne(1L, 1L)).isNotEqualTo(blog2);

    }

    @Test
    @DisplayName("블로그1을 블로그2와 같이 업데이트")
    void updateBlog_WhenGivenBlogIdAndCreatorId() {
        //given
        Member member = new Member(1L, "aa", "aa", "aa");
        Blog blog1 = new Blog(1L, 1L, "title1", "contents1");
        Blog blog2 = new Blog(1L, 2L, "title2", "contents2");
        memberService.join(member);
        List<Blog> blogs = blogSetting(blog1, blog2);

        UpdateBlogForm updateBlogForm = new UpdateBlogForm(blog2.getTitle(), blog2.getContents());

        //when
        blogService.update(blog1.getId(), blog1.getCreatorId(), updateBlogForm);

        //then
        assertThat(blog1.getTitle()).isEqualTo(blog2.getTitle());
        assertThat(blog1.getContents()).isEqualTo(blog2.getContents());

    }

    @Test
    @DisplayName("블로그 생성 테스트")
    void create_WhenGivenBlog_ReturnTrue() {
        //given
        Member member = new Member(1L, "aa", "aa", "aa");
        Blog blog1 = new Blog(1L, 1L, "title1", "contents1");

        //when
        Long saveId = blogService.create(blog1);

        //then
        assertThat(blogRepository.findOneByBlogIdAndCreatorId(saveId, 1L)).isEqualTo(blog1);
    }

    @Test
    void delete_WhenGivenBlogIdAndCreatorID_ThenReturnNull() {
        //given
        Member member = new Member(1L, "aa", "aa", "aa");
        Blog blog1 = new Blog(1L, 1L, "title1", "contents1");
        Blog blog2 = new Blog(1L, 2L, "title2", "contents2");
        memberService.join(member);
        List<Blog> blogs = blogSetting(blog1, blog2);

        //when
        blogService.delete(blog1.getId(), blog1.getCreatorId());

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> blogRepository.findOneByBlogIdAndCreatorId(1L, 1L));//예외가 발생해야 한다.

        //then
        assertThat(e.getMessage()).isEqualTo("해당 블로그가 존재하지 않습니다.");
    }

    private List<Blog> blogSetting(Blog blog1, Blog blog2) {
        blogService.create(blog1);
        blogService.create(blog2);

        List<Blog> blogs = new ArrayList<>();
        blogs.add(blog1);
        blogs.add(blog2);
        return blogs;
    }
}
