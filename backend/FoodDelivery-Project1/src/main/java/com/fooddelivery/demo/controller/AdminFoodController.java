package com.fooddelivery.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.demo.model.FoodItem;
import com.fooddelivery.demo.service.FoodService;

@RestController
@RequestMapping("/admin/food")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    // ✅ ADD FOOD ITEM
    @PostMapping("/add")
    public FoodItem addFood(
            @RequestBody FoodItem food,
            Authentication authentication) {

        String adminEmail = authentication.getName();
        return foodService.addFood(food, adminEmail);
    }

    // ✅ VIEW MY FOOD ITEMS
    @GetMapping("/my")
    public List<FoodItem> getMyFoods(Authentication authentication) {
        return foodService.getMyFoods(authentication.getName());
    }

    // ✅ DELETE FOOD ITEM
    @DeleteMapping("/{id}")
    public void deleteFood(
            @PathVariable Long id,
            Authentication authentication) {

        foodService.deleteFood(id, authentication.getName());
    }
}