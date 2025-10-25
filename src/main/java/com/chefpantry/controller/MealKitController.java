package com.chefpantry.controller;

import com.chefpantry.model.MealKit;
import com.chefpantry.model.User;
import com.chefpantry.service.MealKitService;
import com.chefpantry.service.SubscriptionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mealkits")
public class MealKitController {
    
    @Autowired
    private MealKitService mealKitService;
    
    @Autowired
    private SubscriptionService subscriptionService;
    
    @GetMapping
    public String showMealKits(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        
        // Get ALL available meal kits for browsing
        model.addAttribute("mealKits", mealKitService.getAvailableMealKits());
        model.addAttribute("isLoggedIn", user != null);
        
        return "meal-kits";
    }
    
    @PostMapping("/subscribe")
    public String subscribe(@RequestParam Long mealKitId,
                           @RequestParam String frequency,
                           @RequestParam String deliveryAddress,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Please login to subscribe!");
            return "redirect:/login/user";
        }
        
        try {
            subscriptionService.createSubscription(user.getId(), mealKitId, frequency, deliveryAddress);
            redirectAttributes.addFlashAttribute("success", "🎉 Subscription created successfully! Check your dashboard.");
            return "redirect:/user/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ " + e.getMessage());
            return "redirect:/mealkits";
        }
    }
    
    @PostMapping("/subscription/{id}/pause")
    public String pauseSubscription(@PathVariable Long id, 
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            return "redirect:/login/user";
        }
        
        try {
            subscriptionService.updateSubscriptionStatus(id, "PAUSED");
            redirectAttributes.addFlashAttribute("success", "⏸️ Subscription paused successfully!");
            return "redirect:/user/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ " + e.getMessage());
            return "redirect:/user/dashboard";
        }
    }
    
    @PostMapping("/subscription/{id}/resume")
    public String resumeSubscription(@PathVariable Long id, 
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            return "redirect:/login/user";
        }
        
        try {
            subscriptionService.updateSubscriptionStatus(id, "ACTIVE");
            redirectAttributes.addFlashAttribute("success", "▶️ Subscription resumed successfully!");
            return "redirect:/user/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ " + e.getMessage());
            return "redirect:/user/dashboard";
        }
    }
    
    @PostMapping("/subscription/{id}/cancel")
    public String cancelSubscription(@PathVariable Long id, 
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            return "redirect:/login/user";
        }
        
        try {
            subscriptionService.updateSubscriptionStatus(id, "CANCELLED");
            redirectAttributes.addFlashAttribute("success", "❌ Subscription cancelled successfully!");
            return "redirect:/user/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ " + e.getMessage());
            return "redirect:/user/dashboard";
        }
    }
}