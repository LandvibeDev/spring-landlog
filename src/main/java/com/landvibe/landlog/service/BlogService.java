package com.landvibe.landlog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.BlogRepository;

@Service
public class BlogService {
	private final BlogRepository blogRepository;
	private final MemberService memberService;

	public BlogService(BlogRepository blogRepository, MemberService memberService) {
		this.blogRepository = blogRepository;
		this.memberService = memberService;
	}

	public Long create(Blog blog) {
		validateCreator(blog.getCreatorId());
		validateBlog(blog);
		return blogRepository.save(blog);
	}

	public Long update(Blog blog) {
		validateCreator(blog.getCreatorId());
		findByBlogId(blog.getId());
		validateBlog(blog);
		return blogRepository.update(blog);
	}

	public Long delete(Long blogId) {
		Blog blog =  findByBlogId(blogId);
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

	public void validateCreator(Long creatorId){
		memberService.findById(creatorId);
	}

	public void validateBlog(Blog blog){
		if (blog.getTitle().equals("")) {
			throw new IllegalArgumentException("제목을 입력해주세요.");
		}
		if (blog.getContents().equals("")) {
			throw new IllegalArgumentException("내용을 입력해주세요.");
		}
	}

}
