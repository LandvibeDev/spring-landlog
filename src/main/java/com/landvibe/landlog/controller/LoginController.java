package com.landvibe.landlog.controller;

import com.landvibe.landlog.controller.form.LoginForm;
import com.landvibe.landlog.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/members/login")
    public String showLoginForm() {
        return "members/loginForm";
    }

    @PostMapping("/members/login")
    public String login(LoginForm loginForm, RedirectAttributes redirectAttributes) {
        Long memberId = loginService.login(loginForm.getEmail(), loginForm.getPassword()); // 예외

        redirectAttributes.addAttribute("creatorId", memberId);
        return "redirect:/blogs";
    }

}
