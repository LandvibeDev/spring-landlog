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

	public void create(Blog blog) {
		validateInvalidCreator(blog.getCreatorId());
		blogRepository.save(blog);
	}

	public void update(Blog blog) {
		validateInvalidCreator(blog.getCreatorId());
		findByBlogId(blog.getId());
		blogRepository.update(blog);
	}

	public void delete(Blog blog) {
		validateInvalidCreator(blog.getCreatorId());
		blogRepository.delete(blog.getId());
	}

	public List<Blog> findBlogList(Long creatorId) {
		validateInvalidCreator(creatorId);
		return blogRepository.findBlogListByCreatorId(creatorId);
	}

	public Blog findByBlogId(Long blogId) {
		return blogRepository.findByBlogId(blogId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 블로그입니다."));
	}

	public void validateInvalidCreator(Long creatorId){
		Member creator = memberService.findById(creatorId);
		memberService.validateInvalidMember(creator);
	}

}
