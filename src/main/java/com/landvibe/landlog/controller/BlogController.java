package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.error.LandLogException;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.landvibe.landlog.error.ErrorCode.UNAUTHORIZED_USER;

@Controller
public class BlogController {

    private final MemberService memberService;

    public BlogController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/blogs")
    public String list(@RequestParam long creatorId, Model model) {
        Member member = memberService.findOne(creatorId).orElseThrow(() -> new LandLogException(UNAUTHORIZED_USER)); // 인증
        model.addAttribute("creatorId", member.getId());
        model.addAttribute("name", member.getName());
        return "blogs/blogList";
    }

    @GetMapping(value = "/blogs/new")
    public String create(@RequestParam long creatorId, Model model) {
        Member member = memberService.findOne(creatorId).orElseThrow(() -> new LandLogException(UNAUTHORIZED_USER)); // 인증
        model.addAttribute("creatorId", member.getId());
        model.addAttribute("name", member.getName());
        return "blogs/createBlogForm";
    }

    @GetMapping(value = "/blogs/update")
    public String update(@RequestParam long creatorId, @RequestParam long blogId, Model model) {
        Member member = memberService.findOne(creatorId).orElseThrow(() -> new LandLogException(UNAUTHORIZED_USER)); // 인증
        model.addAttribute("creatorId", member.getId());
        model.addAttribute("name", member.getName());
        model.addAttribute("blogId", blogId);
        return "blogs/updateBlogForm";
    }
}
