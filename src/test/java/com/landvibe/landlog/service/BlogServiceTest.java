package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.exception.LandLogException;
import com.landvibe.landlog.repository.MemoryBlogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

    @InjectMocks
    BlogService blogService;

    @Mock
    MemoryBlogRepository memoryBlogRepository;

    @Mock
    MemberService memberService;

    // member
    Long memberId = 1L;

    // blog
    Long blogId = 1L;
    String title = "title";
    String contents = "contents";

    //mock
    Member mockMember = mock(Member.class);
    List<Blog> mockBlogs = mock(List.class);

    // error
    Long wrongCreatorId = -1L;

    Blog createBlog(Long blogId, String title, String contents, Long memberId) {
        Blog blog = Blog.createBlog(title, contents, memberId);
        blog.setId(blogId);

        return blog;
    }

    @Test
    @DisplayName("createBlog_정상인풋_생성성공")
    void createBlog_whenBlogDetailProvided_returnBlogId() {
        //given
        when(memberService.findMemberById(eq(memberId))).thenReturn(mockMember);
        when(memoryBlogRepository.save(eq(memberId), anyString(), anyString())).thenReturn(blogId);

        //when & then
        Long actual = blogService.createBlog(memberId, title, contents);
        assertThat(actual).isEqualTo(blogId);

        //then
        verify(memberService).findMemberById(eq(memberId));
        verify(memoryBlogRepository).save(eq(memberId), anyString(), anyString());
    }


    @Test
    @DisplayName("createBlog_creatorId_맞지않으면_에러발생")
    void createBlog_whenCreatorIdInvalid_Error() {
        //given
        when(memberService.findMemberById(eq(memberId))).thenThrow(LandLogException.class);

        //when & then
        assertThrows(LandLogException.class,
                () -> blogService.createBlog(memberId, title, contents));

        //then
        verify(memberService, times(1)).findMemberById(eq(memberId));
    }

    @Test
    @DisplayName("findBlogsByCreatorId_정상수행")
    void findBlogsByCreatorId_whenNormalInput_Success() {
        //given
        when(memoryBlogRepository.findByCreatorId(eq(memberId))).thenReturn(mockBlogs);

        //when & then
        List<Blog> actual = blogService.findBlogsByCreatorId(memberId);
        assertThat(actual).isEqualTo(mockBlogs);

        //then
        verify(memoryBlogRepository).findByCreatorId(eq(memberId));
    }

    @Test
    @DisplayName("findBlogsByCreatorId_creatorId가맞지않으면_에러발생")
    void findBlogsByCreatorId_whenMemberIdInvalid_Error() {
        //given
        when(memoryBlogRepository.findByCreatorId(eq(memberId))).thenThrow(LandLogException.class);

        //when & then
        assertThrows(LandLogException.class,
                () -> blogService.findBlogsByCreatorId(memberId));

        //then
        verify(memoryBlogRepository, times(1)).findByCreatorId(eq(memberId));
    }

    @Test
    @DisplayName("findBlogByBlogIdAndCreatorId_블로그id가맞지않으면_에러")
    void findBlogByBlogIdAndCreatorId_whenBlogIdInvalid_Error() {
        //given
        when(memoryBlogRepository.findById(eq(blogId))).thenThrow(LandLogException.class);

        //when & then
        assertThrows(LandLogException.class,
                () -> blogService.findBlogByBlogIdAndCreatorId(memberId, blogId));

        //then
        verify(memoryBlogRepository, times(1)).findById(eq(memberId));
    }

    @Test
    @DisplayName("findBlogByBlogIdAndCreatorId_creatorId가맞지않으면_에러")
    void findBlogByBlogIdAndCreatorId_whenCreatorIdInvalid_Error() {
        //given
        Blog blog = createBlog(blogId, title, contents, memberId);
        when(memoryBlogRepository.findById(eq(blogId))).thenReturn(Optional.of(blog));

        //when & then
        assertThrows(LandLogException.class,
                () -> blogService.findBlogByBlogIdAndCreatorId(wrongCreatorId, blogId));

        //then
        verify(memoryBlogRepository).findById(eq(blogId));
    }

    @Test
    @DisplayName("findBlogByBlogIdAndCreatorId_creatorId와 blog의 creatorId 불일치_에러")
    void findBlogByBlogIdAndMemberId_MisMatchCreatorId_Error() {
        //given
        when(memoryBlogRepository.findById(eq(blogId))).thenThrow(LandLogException.class);

        //when & then
        assertThrows(LandLogException.class,
                () -> blogService.findBlogByBlogIdAndCreatorId(memberId, blogId));

        //then
        verify(memoryBlogRepository, times(1)).findById(eq(memberId));
    }

    @Test
    @DisplayName("updateBlog_creatorId조회불가_에러")
    void updateBlog_creatorIdInvalid_Error() {
        //given
        when(memberService.findMemberById(eq(memberId))).thenThrow(LandLogException.class);

        //when & then
        assertThrows(LandLogException.class,
                () -> blogService.updateBlog(memberId, blogId, title, contents));

        //then
        verify(memberService, times(1)).findMemberById(eq(memberId));
    }

    @Test
    @DisplayName("updateBlog_creatorId와 blog의 creatorId 불일치_에러")
    void updateBlog_BlogIdInvalid_Error() {
        //given
        when(memberService.findMemberById(eq(wrongCreatorId))).thenReturn(mockMember);

        //when & then
        assertThrows(LandLogException.class,
                () -> blogService.updateBlog(wrongCreatorId, blogId, title, contents));

        //then
        verify(memberService).findMemberById(eq(wrongCreatorId));
    }

    @Test
    @DisplayName("deleteBlog_creatorId조회불가_에러")
    void deleteBlog_CreatorIdInvalid_Error() {
        //given
        when(memberService.findMemberById(eq(memberId))).thenThrow(LandLogException.class);

        //when & then
        assertThrows(LandLogException.class,
                () -> blogService.deleteBlog(memberId, blogId));

        //then
        verify(memberService, times(1)).findMemberById(eq(memberId));
    }

    @Test
    @DisplayName("deleteBlog_creatorId와 blog의 creatorId 불일치_에러")
    void deleteBlog_BlogIdInvalid_Error() {
        //given
        when(memberService.findMemberById(eq(wrongCreatorId))).thenReturn(mockMember);

        //when & then
        assertThrows(LandLogException.class,
                () -> blogService.deleteBlog(wrongCreatorId, blogId));

        //then
        verify(memberService).findMemberById(eq(wrongCreatorId));
    }
}
