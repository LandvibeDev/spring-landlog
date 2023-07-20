package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemberRepository;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import com.landvibe.landlog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class BlogController {
    MemoryMemberRepository memoryMemberRepository;

    public BlogController(MemoryMemberRepository memberRepository) {
        this.memoryMemberRepository = memberRepository;
    }

    @GetMapping("/blogs")
    public String blogList(@RequestParam Long creatorId, Model model) {
        Member member = memoryMemberRepository.findById(creatorId).get();
        model.addAttribute("name", member.getName());
        return "blogs/blogList";
    }

}
