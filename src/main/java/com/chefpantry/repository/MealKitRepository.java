package com.chefpantry.repository;

import com.chefpantry.model.MealKit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MealKitRepository extends JpaRepository<MealKit, Long> {
    List<MealKit> findByAvailable(Boolean available);
    List<MealKit> findByCategory(String category);
}