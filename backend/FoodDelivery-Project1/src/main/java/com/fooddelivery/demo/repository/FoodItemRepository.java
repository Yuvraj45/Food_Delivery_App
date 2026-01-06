package com.fooddelivery.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddelivery.demo.model.FoodItem;
import com.fooddelivery.demo.model.User;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

    // ADMIN → view ONLY active foods
    List<FoodItem> findByAdminAndAvailableTrue(User admin);

    // (optional – keep if needed elsewhere)
    List<FoodItem> findByAdmin(User admin);

    // USER → view only available foods
    List<FoodItem> findByAvailable(boolean available);

    List<FoodItem> findByAvailableTrue();
}