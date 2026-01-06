package com.fooddelivery.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddelivery.demo.model.CartItem;
import com.fooddelivery.demo.model.FoodItem;
import com.fooddelivery.demo.model.User;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCustomer(User customer);

    void deleteByCustomer(User customer);

    // ✅ IMPORTANT: find existing cart item
    Optional<CartItem> findByCustomerAndFoodItem(User customer, FoodItem foodItem);
}