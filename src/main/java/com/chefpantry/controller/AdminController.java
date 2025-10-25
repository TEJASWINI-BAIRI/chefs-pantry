package com.chefpantry.controller;

import com.chefpantry.model.Admin;
import com.chefpantry.model.MealKit;
import com.chefpantry.service.AdminService;
import com.chefpantry.service.MealKitService;
import com.chefpantry.service.SubscriptionService;
import com.chefpantry.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private MealKitService mealKitService;
    
    @Autowired
    private SubscriptionService subscriptionService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AdminService adminService;
    
    @PostMapping("/login")
    public String loginAdmin(@RequestParam String email,
                            @RequestParam String password,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        try {
            Admin admin = adminService.loginAdmin(email, password);
            session.setAttribute("admin", admin);
            session.setAttribute("userType", "ADMIN");
            return "redirect:/admin/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/login/admin";
        }
    }
    
    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("admin");
        String userType = (String) session.getAttribute("userType");
        
        if (admin == null || !"ADMIN".equals(userType)) {
            return "redirect:/login/admin";
        }
        
        model.addAttribute("admin", admin);
        model.addAttribute("totalUsers", userService.getAllUsers().size());
        model.addAttribute("totalMealKits", mealKitService.getAllMealKits().size());
        model.addAttribute("activeSubscriptions", subscriptionService.getActiveSubscriptions().size());
        model.addAttribute("recentSubscriptions", subscriptionService.getAllSubscriptions());
        
        return "admin/admin-dashboard";
    }
    
    @GetMapping("/mealkits")
    public String manageMealKits(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("admin");
        String userType = (String) session.getAttribute("userType");
        
        if (admin == null || !"ADMIN".equals(userType)) {
            return "redirect:/login/admin";
        }
        
        model.addAttribute("mealKits", mealKitService.getAllMealKits());
        return "admin/manage-mealkits";
    }
    
    @PostMapping("/mealkits/add")
    public String addMealKit(@ModelAttribute MealKit mealKit, 
                            RedirectAttributes redirectAttributes) {
        try {
            mealKitService.createMealKit(mealKit);
            redirectAttributes.addFlashAttribute("success", "Meal Kit added successfully!");
            return "redirect:/admin/mealkits";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/mealkits";
        }
    }
    
    @PostMapping("/mealkits/update/{id}")
    public String updateMealKit(@PathVariable Long id,
                               @ModelAttribute MealKit mealKit,
                               RedirectAttributes redirectAttributes) {
        try {
            mealKitService.updateMealKit(id, mealKit);
            redirectAttributes.addFlashAttribute("success", "Meal Kit updated successfully!");
            return "redirect:/admin/mealkits";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/mealkits";
        }
    }
    
    @PostMapping("/mealkits/delete/{id}")
    public String deleteMealKit(@PathVariable Long id, 
                               RedirectAttributes redirectAttributes) {
        try {
            mealKitService.deleteMealKit(id);
            redirectAttributes.addFlashAttribute("success", "Meal Kit deleted successfully!");
            return "redirect:/admin/mealkits";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/mealkits";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}