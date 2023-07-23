package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.form.BlogUpdateForm;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryBlogRepository;
import com.landvibe.landlog.repository.MemoryMemberRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class BlogServiceTest {

	BlogService blogService;
	MemberService memberService;
	MemoryMemberRepository memberRepository;
	MemoryBlogRepository blogRepository;
	Member member;

	@BeforeEach
	public void beforeEach() {
		memberRepository = new MemoryMemberRepository();
		blogRepository = new MemoryBlogRepository();
		memberService = new MemberService(memberRepository);
		blogService = new BlogService(blogRepository, memberService);
		member = new Member("name", "email@landvibe.com", "password");
		memberService.join(member);
	}

	@AfterEach
	public void afterEach() {
		memberRepository.clearStore();
		blogRepository.clearStore();
	}

	String title = "제목";
	String contents = "내용";
	@Test
	void 잘못된_작성자_id() {
		Long invalidCreatorId = 0L;
		Blog blog = new Blog(invalidCreatorId, title, contents);

		Exception e = assertThrows(Exception.class,
			() -> blogService.create(blog));
		assertThat(e.getMessage()).isEqualTo("존재하지 않는 회원입니다.");
	}

	@Test
	void 제목_입력안함() {

		String invalidTitle = "";
		Blog blog = new Blog(member.getId(), invalidTitle, contents);

		Exception e = assertThrows(Exception.class,
			() -> blogService.create(blog));
		assertThat(e.getMessage()).isEqualTo("제목을 입력해주세요.");

	}

	@Test
	void 내용_입력안함() {

		String invalidContents = "";
		Blog blog = new Blog(member.getId(), title, invalidContents);

		Exception e = assertThrows(Exception.class,
			() -> blogService.create(blog));
		assertThat(e.getMessage()).isEqualTo("내용을 입력해주세요.");

	}


}
