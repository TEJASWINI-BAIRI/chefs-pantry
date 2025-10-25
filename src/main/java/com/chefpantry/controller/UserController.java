package com.chefpantry.controller;

import com.chefpantry.model.User;
import com.chefpantry.service.UserService;
import com.chefpantry.service.SubscriptionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private SubscriptionService subscriptionService;
    
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/login/user";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }
    
    @PostMapping("/login")
    public String loginUser(@RequestParam String email, 
                           @RequestParam String password,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        try {
            User user = userService.loginUser(email, password);
            session.setAttribute("user", user);
            session.setAttribute("userType", "USER");
            return "redirect:/user/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/login/user";
        }
    }
    
    @GetMapping("/dashboard")
    public String userDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        String userType = (String) session.getAttribute("userType");
        
        if (user == null || !"USER".equals(userType)) {
            return "redirect:/login/user";
        }
        
        model.addAttribute("user", user);
        model.addAttribute("subscriptions", subscriptionService.getUserSubscriptions(user.getId()));
        return "user-dashboard";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, 
                            @ModelAttribute User user,
                            RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(id, user);
            redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
            return "redirect:/user/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/dashboard";
        }
    }
}