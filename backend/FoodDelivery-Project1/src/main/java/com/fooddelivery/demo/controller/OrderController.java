package com.fooddelivery.demo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.demo.model.*;
import com.fooddelivery.demo.service.OrderService;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ================= USER → PLACE ORDER =================
    @PostMapping("/place-from-cart")
    @PreAuthorize("hasRole('USER')")
    public Order placeOrderFromCart(
            Authentication authentication,
            @RequestParam Long adminId,
            @RequestParam PaymentMode paymentMode) {

        return orderService.placeOrderFromCart(
                authentication.getName(),
                adminId,
                paymentMode
        );
    }

    // ================= USER → MY ORDERS =================
    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public List<Order> getMyOrders(Authentication authentication) {
        return orderService.getMyOrders(authentication.getName());
    }

    // ================= USER → PAYMENT UPDATE =================
    @PutMapping("/payment-status/{orderId}")
    @PreAuthorize("hasRole('USER')")
    public Order updatePaymentStatus(
            @PathVariable Long orderId,
            @RequestParam PaymentStatus status) {

        return orderService.updatePaymentStatus(orderId, status);
    }
}