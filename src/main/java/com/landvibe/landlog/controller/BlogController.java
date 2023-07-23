package com.landvibe.landlog.controller;

import java.util.List;

import com.landvibe.landlog.controller.form.BlogForm;
import com.landvibe.landlog.controller.form.BlogUpdateForm;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("blogs")
public class BlogController {

	private final MemberService memberService;
	private final BlogService blogService;

	public BlogController(MemberService memberService, BlogService blogService) {
		this.memberService = memberService;
		this.blogService = blogService;
	}

	@GetMapping
	public String blog(@RequestParam(name = "creatorId") Long creatorId, Model model) {
		List<Blog> blogs = blogService.findBlogList(creatorId);
		model.addAttribute("creatorId", creatorId);
		model.addAttribute("name", memberService.findById(creatorId).getName());
		model.addAttribute("blogs", blogs);
		return "blogs/blogList";
	}

	@GetMapping("/new")
	public String createBlogForm(@RequestParam(name = "creatorId") Long creatorId, Model model) {
		model.addAttribute("creatorId", creatorId);
		model.addAttribute("name", memberService.findById(creatorId).getName());
		return "blogs/createBlogForm";
	}

	@PostMapping("/new")
	public String createBlog(@RequestParam(name = "creatorId") Long creatorId, BlogForm blogForm,
		RedirectAttributes redirectAttributes) {
		Blog blog = new Blog(creatorId, blogForm.getTitle(), blogForm.getContents());
		blogService.create(blog);
		redirectAttributes.addAttribute("creatorId", creatorId);
		return "redirect:/blogs";
	}

	@GetMapping("/update")
	public String updateBlogForm(@RequestParam(name = "creatorId") Long creatorId,
		@RequestParam(name = "blogId") Long blogId, Model model) {

		Blog blog = blogService.findByBlogId(blogId);
		String name = memberService.findById(creatorId).getName();

		model.addAttribute("creatorId", creatorId);
		model.addAttribute("blog", blog);
		model.addAttribute("name", name);

		return "blogs/updateBlogForm";
	}

	@PostMapping("/update")
	public String updateBlog(BlogUpdateForm form, RedirectAttributes redirectAttributes) {
		Blog blog = new Blog(form.getCreatorId(), form.getTitle(), form.getContents());
		blog.setId(form.getId());
		blogService.update(blog);
		redirectAttributes.addAttribute("creatorId", form.getCreatorId());
		return "redirect:/blogs";
	}

	@PostMapping("/delete")
	public String deleteBlog(@RequestParam(name = "creatorId") Long creatorId,
		@RequestParam(name = "blogId") Long blogId,
		RedirectAttributes redirectAttributes) {
		blogService.delete(blogId);
		redirectAttributes.addAttribute("creatorId", creatorId);
		return "redirect:/blogs";
	}
}

