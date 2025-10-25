package com.chefpantry.service;

import com.chefpantry.model.MealKit;
import com.chefpantry.repository.MealKitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealKitService {

    @Autowired
    private MealKitRepository mealKitRepository;

    public MealKit createMealKit(MealKit mealKit) {
        return mealKitRepository.save(mealKit);
    }

    public List<MealKit> getAllMealKits() {
        return mealKitRepository.findAll();
    }

    public List<MealKit> getAvailableMealKits() {
        return mealKitRepository.findByAvailable(true);
    }

    public MealKit getMealKitById(Long id) {
        return mealKitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meal Kit not found!"));
    }

    public List<MealKit> getMealKitsByCategory(String category) {
        return mealKitRepository.findByCategory(category);
    }

    public MealKit updateMealKit(Long id, MealKit updatedMealKit) {
        MealKit mealKit = getMealKitById(id);
        mealKit.setName(updatedMealKit.getName());
        mealKit.setDescription(updatedMealKit.getDescription());
        mealKit.setPrice(updatedMealKit.getPrice());
        mealKit.setImageUrl(updatedMealKit.getImageUrl());
        mealKit.setIngredients(updatedMealKit.getIngredients());
        mealKit.setInstructions(updatedMealKit.getInstructions());
        mealKit.setCategory(updatedMealKit.getCategory());
        mealKit.setServings(updatedMealKit.getServings());
        mealKit.setPrepTime(updatedMealKit.getPrepTime());
        mealKit.setAvailable(updatedMealKit.getAvailable());
        return mealKitRepository.save(mealKit);
    }

    public void deleteMealKit(Long id) {
        mealKitRepository.deleteById(id);
    }
}