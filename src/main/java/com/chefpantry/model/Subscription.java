package com.chefpantry.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
public class Subscription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "meal_kit_id", nullable = false)
    private MealKit mealKit;
    
    @Column(nullable = false)
    private String frequency; // WEEKLY, MONTHLY
    
    @Column(nullable = false)
    private String status = "ACTIVE"; // ACTIVE, PAUSED, CANCELLED
    
    @Column(name = "start_date")
    private LocalDateTime startDate = LocalDateTime.now();
    
    @Column(name = "next_delivery")
    private LocalDateTime nextDelivery;
    
    private String deliveryAddress;
    
    // Constructors
    public Subscription() {}
    
    public Subscription(User user, MealKit mealKit, String frequency) {
        this.user = user;
        this.mealKit = mealKit;
        this.frequency = frequency;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public MealKit getMealKit() {
        return mealKit;
    }
    
    public void setMealKit(MealKit mealKit) {
        this.mealKit = mealKit;
    }
    
    public String getFrequency() {
        return frequency;
    }
    
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public LocalDateTime getNextDelivery() {
        return nextDelivery;
    }
    
    public void setNextDelivery(LocalDateTime nextDelivery) {
        this.nextDelivery = nextDelivery;
    }
    
    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}