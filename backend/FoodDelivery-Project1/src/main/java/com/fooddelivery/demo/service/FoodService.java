package com.fooddelivery.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fooddelivery.demo.model.FoodItem;
import com.fooddelivery.demo.model.User;
import com.fooddelivery.demo.repository.FoodItemRepository;
import com.fooddelivery.demo.repository.UserRepository;

@Service
public class FoodService {

    private final FoodItemRepository foodItemRepository;
    private final UserRepository userRepository;

    public FoodService(FoodItemRepository foodItemRepository,
                       UserRepository userRepository) {
        this.foodItemRepository = foodItemRepository;
        this.userRepository = userRepository;
    }

    // ✅ ADMIN → ADD FOOD ITEM
    public FoodItem addFood(FoodItem food, String adminEmail) {

        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        food.setAdmin(admin);
        food.setAvailable(true); // ensure default
        return foodItemRepository.save(food);
    }

    // ✅ ADMIN → VIEW OWN (ONLY ACTIVE) FOOD ITEMS
    public List<FoodItem> getMyFoods(String adminEmail) {

        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        return foodItemRepository.findByAdminAndAvailableTrue(admin);
    }

    // ✅ ADMIN → SOFT DELETE FOOD
    public void deleteFood(Long foodId, String adminEmail) {

        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        FoodItem food = foodItemRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        if (!food.getAdmin().getId().equals(admin.getId())) {
            throw new RuntimeException("Unauthorized to delete this food");
        }

        // ✅ SOFT DELETE
        food.setAvailable(false);
        foodItemRepository.save(food);
    }
}