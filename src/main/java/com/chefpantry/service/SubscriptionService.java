package com.chefpantry.service;

import com.chefpantry.model.MealKit;
import com.chefpantry.model.Subscription;
import com.chefpantry.model.User;
import com.chefpantry.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubscriptionService {
    
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private MealKitService mealKitService;
    
    public Subscription createSubscription(Long userId, Long mealKitId, String frequency, String deliveryAddress) {
        User user = userService.getUserById(userId);
        MealKit mealKit = mealKitService.getMealKitById(mealKitId);
        
        Subscription subscription = new Subscription(user, mealKit, frequency);
        subscription.setDeliveryAddress(deliveryAddress);
        
        // Calculate next delivery date
        LocalDateTime nextDelivery = LocalDateTime.now();
        if (frequency.equals("WEEKLY")) {
            nextDelivery = nextDelivery.plusWeeks(1);
        } else if (frequency.equals("MONTHLY")) {
            nextDelivery = nextDelivery.plusMonths(1);
        }
        subscription.setNextDelivery(nextDelivery);
        
        return subscriptionRepository.save(subscription);
    }
    
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }
    
    public List<Subscription> getUserSubscriptions(Long userId) {
        User user = userService.getUserById(userId);
        return subscriptionRepository.findByUser(user);
    }
    
    public Subscription getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found!"));
    }
    
    public Subscription updateSubscriptionStatus(Long id, String status) {
        Subscription subscription = getSubscriptionById(id);
        subscription.setStatus(status);
        return subscriptionRepository.save(subscription);
    }
    
    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }
    
    public List<Subscription> getActiveSubscriptions() {
        return subscriptionRepository.findByStatus("ACTIVE");
    }
}