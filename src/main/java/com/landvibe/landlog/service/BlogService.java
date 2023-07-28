package com.landvibe.landlog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.landvibe.landlog.controller.form.BlogForm;
import com.landvibe.landlog.controller.form.BlogUpdateForm;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.repository.BlogRepository;

@Service
public class BlogService {
	private final BlogRepository blogRepository;
	private final MemberService memberService;

	public BlogService(BlogRepository blogRepository, MemberService memberService) {
		this.blogRepository = blogRepository;
		this.memberService = memberService;
	}

	public Long create(Long creatorId, BlogForm blogForm) {
		Blog blog = new Blog(creatorId, blogForm.getTitle(), blogForm.getContents());

		validateCreator(creatorId);
		validateBlog(blog.getTitle(), blog.getContents());
		return blogRepository.save(blog);
	}

	public Long update(BlogUpdateForm updateForm) {
		validateCreator(updateForm.getCreatorId());
		validateBlog(updateForm.getTitle(), updateForm.getContents());

		Long blogId = findByBlogId(updateForm.getId()).getId();
		Blog newBlog = new Blog(updateForm.getCreatorId(), updateForm.getTitle(), updateForm.getContents());

		return blogRepository.update(blogId, newBlog);
	}

	public Long delete(Long blogId) {
		Blog blog = findByBlogId(blogId);
		validateCreator(blog.getCreatorId());
		return blogRepository.delete(blog.getId());
	}

	public List<Blog> findBlogList(Long creatorId) {
		validateCreator(creatorId);
		return blogRepository.findBlogListByCreatorId(creatorId);
	}

	public Blog findByBlogId(Long blogId) {
		return blogRepository.findByBlogId(blogId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 블로그입니다."));
	}

	public void validateCreator(Long creatorId) {
		memberService.findById(creatorId);
	}

	public void validateBlog(String title, String contents) {
		if (title.equals("")) {
			throw new IllegalArgumentException("제목을 입력해주세요.");
		}
		if (contents.equals("")) {
			throw new IllegalArgumentException("내용을 입력해주세요.");
		}
	}

}
