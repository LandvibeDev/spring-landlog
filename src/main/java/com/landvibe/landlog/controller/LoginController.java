package com.landvibe.landlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/members/login")
    public String loginForm(){
        return "members/loginForm";
    }



}
