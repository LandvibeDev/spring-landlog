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


	public void validateInvalidCreator(Long creatorId){
		Member creator = memberService.findById(creatorId);
		memberService.validateInvalidMember(creator);
	}

}
