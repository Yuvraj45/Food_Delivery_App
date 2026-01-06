package com.fooddelivery.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.demo.model.Order;
import com.fooddelivery.demo.model.PaymentStatus;
import com.fooddelivery.demo.service.OrderService;

@RestController
@RequestMapping("/payment")
@PreAuthorize("hasRole('USER')")   // 🔐 SECURITY FIX
public class PaymentController {

    private final OrderService orderService;

    public PaymentController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ✅ MOCK PAYMENT CONFIRMATION (UPI / NET BANKING / COD)
    @PutMapping("/{orderId}/status")
    public Order updatePaymentStatus(
            @PathVariable Long orderId,
            @RequestParam PaymentStatus status) {

        return orderService.updatePaymentStatus(orderId, status);
    }
}