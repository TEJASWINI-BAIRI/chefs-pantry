package com.chefpantry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/login-choice")
    public String loginChoice() {
        return "login-choice";
    }
    
    @GetMapping("/login/user")
    public String userLogin() {
        return "login-user";
    }
    
    @GetMapping("/login/admin")
    public String adminLogin() {
        return "login-admin";
    }
    
    @GetMapping("/register")
    public String register() {
        return "register";
    }
}