package com.landvibe.landlog.controller;

import java.util.Optional;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.service.MemberService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LandLogController implements BlogController {

	private final MemberService memberService;

	public LandLogController(MemberService memberService) {
		this.memberService = memberService;
	}

	@Override
	@GetMapping("/blogs")
	public String blog(@RequestParam Long creatorId, Model model) {
		Member member = memberService.findById(creatorId);
		model.addAttribute("name", member.getName());
		return "blog/blogList";
	}

}
