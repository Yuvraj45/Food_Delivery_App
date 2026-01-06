package com.fooddelivery.demo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.demo.model.CartItem;
import com.fooddelivery.demo.model.FoodItem;
import com.fooddelivery.demo.model.User;
import com.fooddelivery.demo.repository.CartItemRepository;
import com.fooddelivery.demo.repository.FoodItemRepository;
import com.fooddelivery.demo.repository.UserRepository;

@RestController
@RequestMapping("/cart")
@PreAuthorize("hasRole('USER')")
public class CartController {

    private final CartItemRepository cartItemRepository;
    private final FoodItemRepository foodItemRepository;
    private final UserRepository userRepository;

    public CartController(
            CartItemRepository cartItemRepository,
            FoodItemRepository foodItemRepository,
            UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.foodItemRepository = foodItemRepository;
        this.userRepository = userRepository;
    }

    // ================= VIEW CART =================
    @GetMapping
    public List<CartItem> viewCart(Authentication auth) {

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartItemRepository.findByCustomer(user);
    }

    // ================= ADD / INCREMENT =================
    @PostMapping("/add/{foodId}")
    public CartItem addToCart(
            @PathVariable Long foodId,
            Authentication auth) {

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        FoodItem food = foodItemRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        // ✅ CHECK IF ALREADY EXISTS
        CartItem existingItem =
                cartItemRepository.findByCustomerAndFoodItem(user, food)
                        .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            return cartItemRepository.save(existingItem);
        }

        // ✅ NEW ITEM ONLY IF NOT EXISTS
        CartItem item = new CartItem();
        item.setCustomer(user);
        item.setFoodItem(food);
        item.setQuantity(1);

        return cartItemRepository.save(item);
    }

    // ================= DECREMENT =================
    @PutMapping("/decrement/{cartItemId}")
    public void decrement(@PathVariable Long cartItemId) {

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (item.getQuantity() <= 1) {
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(item.getQuantity() - 1);
            cartItemRepository.save(item);
        }
    }

    // ================= REMOVE ITEM =================
    @DeleteMapping("/{cartItemId}")
    public void removeItem(@PathVariable Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    // ================= CLEAR CART =================
    @DeleteMapping("/clear")
    public void clearCart(Authentication auth) {

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        cartItemRepository.deleteByCustomer(user);
    }
}