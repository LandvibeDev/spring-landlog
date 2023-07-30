package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.form.BlogForm;
import com.landvibe.landlog.controller.form.BlogUpdateForm;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryBlogRepository;
import com.landvibe.landlog.repository.MemoryMemberRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {

	@Mock
	private MemoryBlogRepository blogRepository;

	@Mock
	private MemberService memberService;

	@Mock
	private MemoryMemberRepository memberRepository;


	BlogService blogService;

	String title = "제목";
	String contents = "내용";
	String updatedTitle = "새 제목";
	String updatedContents = "새 내용";
	String invalidTitle = "";
	String invalidContents = "";
	Long creatorId = 1L;
	Long blogId = 1L;

	BlogForm blogForm;
	BlogUpdateForm updateForm;
	Blog blog;
	Blog updateBlog;
	Member member;

	@BeforeEach
	public void beforeEach() {
		blogService = new BlogService(blogRepository,memberService);
		blogForm = new BlogForm(title, contents);
		updateForm = new BlogUpdateForm(creatorId, blogId, updatedTitle, updatedContents);
		blog = new Blog(creatorId, blogForm.getTitle(), blogForm.getContents());
		updateBlog = new Blog(updateForm.getCreatorId(),updateForm.getTitle(),updateForm.getContents());
		blog.setId(blogId);
		updateBlog.setId(blogId);
		member = new Member("name","email@landvibe.com","password");
	}

	@AfterEach
	public void afterEach(){
		blogRepository.clearStore();
	}

	@DisplayName("블로그 생성 성공")
	@Test
	public void create() {
		when(blogRepository.save(any(Blog.class))).thenReturn(blogId);
		assertEquals(blogId, blogService.create(creatorId, blogForm));
		verify(blogRepository, times(1)).save(any(Blog.class));
	}

	@DisplayName("블로그 생성 실패 -> 잘못된 제목")
	@Test
	public void create_invalidTitle(){
		BlogForm invalidBlogForm = new BlogForm(invalidTitle,contents);

		Exception e = assertThrows(Exception.class,
			() -> blogService.create(creatorId,invalidBlogForm));
		assertEquals(e.getMessage(),"제목을 입력해주세요.");
	}

	@DisplayName("블로그 생성 실패 -> 잘못된 내용")
	@Test
	public void create_invalidContents(){
		BlogForm invalidBlogForm = new BlogForm(title,invalidContents);

		Exception e = assertThrows(Exception.class,
			() -> blogService.create(creatorId,invalidBlogForm));
		assertEquals(e.getMessage(),"내용을 입력해주세요.");
	}

	@DisplayName("블로그 업데이트 성공")
	@Test
	public void update(){
		when(blogRepository.findByBlogId(any(Long.class))).thenReturn(Optional.ofNullable(updateBlog));
		when(blogRepository.update(any(Long.class),any(Blog.class))).thenReturn(blogId);

		Long updateBlogId = blogService.update(updateForm);
		Blog targetBlog = blogService.findByBlogId(updateBlogId);

		assertEquals(blogId,updateBlogId);
		assertEquals(updatedTitle,targetBlog.getTitle());
		assertEquals(updatedContents,targetBlog.getContents());
		verify(blogRepository, times(1)).update(any(Long.class), any(Blog.class));
	}

	@DisplayName("블로그 업데이트 실패 -> 잘못된 제목")
	@Test
	public void update_invalidTitle(){
		BlogUpdateForm invalidUpdateForm = new BlogUpdateForm(creatorId,blogId,invalidTitle,updatedContents);

		Exception e = assertThrows(Exception.class,
			() -> blogService.update(invalidUpdateForm));
		assertEquals(e.getMessage(),"제목을 입력해주세요.");
	}

	@DisplayName("블로그 업데이트 실패 -> 잘못된 내용")
	@Test
	public void update_invalidContents(){
		BlogUpdateForm invalidUpdateForm = new BlogUpdateForm(creatorId,blogId,updatedTitle,invalidContents);

		Exception e = assertThrows(Exception.class,
			() -> blogService.update(invalidUpdateForm));
		assertEquals(e.getMessage(),"내용을 입력해주세요.");
	}

	@DisplayName("블로그 삭제 성공")
	@Test
	public void delete() {
		when(blogRepository.delete(blogId)).thenReturn(blogId);
		when(blogRepository.findByBlogId(blogId)).thenReturn(Optional.ofNullable(blog));

		blogService.delete(blogId);

		verify(blogRepository,times(1)).delete(blogId);
	}
	@DisplayName("블로그 삭제 실패")
	@Test
	public void delete_fail() {

		blogService.create(creatorId, blogForm);

		Exception e = assertThrows(Exception.class,
			() -> blogService.delete(0L));
		assertEquals(e.getMessage(),"존재하지 않는 블로그입니다.");

		verify(blogRepository, times(1)).findByBlogId(any(Long.class));
		verify(blogRepository, never()).delete(any(Long.class));
	}
}