package com.fooddelivery.demo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.demo.model.FoodItem;
import com.fooddelivery.demo.model.User;
import com.fooddelivery.demo.repository.FoodItemRepository;
import com.fooddelivery.demo.repository.UserRepository;
import com.fooddelivery.demo.service.FoodService;

@RestController
@RequestMapping("/foods")
@CrossOrigin(origins = "*")
public class FoodController {

    private final FoodService foodService;
    private final FoodItemRepository foodItemRepository;
    private final UserRepository userRepository;

    // ✅ SINGLE CONSTRUCTOR (CORRECT)
    public FoodController(
            FoodService foodService,
            FoodItemRepository foodItemRepository,
            UserRepository userRepository) {

        this.foodService = foodService;
        this.foodItemRepository = foodItemRepository;
        this.userRepository = userRepository;
    }

    // ================= ADMIN =================

    // ✅ ADMIN: Add food
    @PostMapping("/admin/add")
    @PreAuthorize("hasRole('ADMIN')")
    public FoodItem addFood(
            @RequestBody FoodItem food,
            Authentication authentication) {

        return foodService.addFood(food, authentication.getName());
    }

    // ✅ ADMIN: View own foods
    @GetMapping("/admin/my-foods")
    @PreAuthorize("hasRole('ADMIN')")
    public List<FoodItem> getMyFoods(Authentication authentication) {

        User admin = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        return foodItemRepository.findByAdmin(admin);
    }

    // ✅ ADMIN: Toggle availability
    @PutMapping("/admin/{foodId}/toggle")
    @PreAuthorize("hasRole('ADMIN')")
    public FoodItem toggleAvailability(@PathVariable Long foodId) {

        FoodItem food = foodItemRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        food.setAvailable(!food.isAvailable());
        return foodItemRepository.save(food);
    }

    // ================= USER =================

    // ✅ USER: View available foods
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN','DELIVERY')")
    public List<FoodItem> getAvailableFoods() {

        return foodItemRepository.findByAvailableTrue();
    }
}