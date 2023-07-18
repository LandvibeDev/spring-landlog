package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.service.MemberService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class MemberController {
	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping(value = "/members/new")
	public String createForm() {
		return "members/createMemberForm";
	}

	@PostMapping(value = "/members/new")
	public String create(MemberForm form) {
		Member member = new Member();
		member.setName(form.getName());
		member.setEmail(form.getEmail());
		member.setPassword(form.getPassword());
		memberService.join(member);
		return "redirect:/";
	}

	@GetMapping(value = "/members")
	public String list(Model model) {
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		return "members/memberList";
	}

	@GetMapping(value = "/members/login")
	public String loginForm() {
		return "members/loginForm";
	}

	@PostMapping(value = "/members/login")
	public String login(MemberForm form, RedirectAttributes redirect) {
		String email = form.getEmail();
		String password = form.getPassword();
		Optional<Member> loginMember = memberService.login(email, password);

		if (loginMember.isEmpty()) {
			return "redirect:/";
		}

		Member member = loginMember.orElse(null);
		redirect.addAttribute("creatorId", member.getId());

		return "redirect:/blogs/{creatorId}";
	}

	@GetMapping(value = "/blogs/{creatorId}")
	public String blog(@PathVariable Long creatorId, Model model) {
		Optional<Member> blogMember = memberService.findOne(creatorId);

		if (blogMember.isEmpty()) {
			return "redirect:/";
		}

		Member member = blogMember.orElse(null);
		model.addAttribute("member", member);

		return "members/blog";
	}

}
