package com.fooddelivery.demo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.demo.dto.mapper.OrderMapper;
import com.fooddelivery.demo.dto.response.OrderResponseDTO;
import com.fooddelivery.demo.model.Order;
import com.fooddelivery.demo.model.OrderStatus;
import com.fooddelivery.demo.service.OrderService;

@RestController
@RequestMapping("/orders/delivery")
@PreAuthorize("hasRole('DELIVERY')")
@CrossOrigin(origins = "*")
public class DeliveryController {

    private final OrderService orderService;

    public DeliveryController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ================= AVAILABLE DELIVERY REQUESTS =================
    @GetMapping("/requests")
    public List<OrderResponseDTO> getDeliveryRequests() {
        return orderService.getDeliveryRequests()
                .stream()
                .map(OrderMapper::toOrderResponse)
                .toList();
    }

    // ================= ACCEPT DELIVERY =================
    @PutMapping("/{orderId}/accept")
    public OrderResponseDTO acceptDelivery(
            @PathVariable Long orderId,
            Authentication auth) {

        Order order = orderService.acceptDelivery(orderId, auth.getName());
        return OrderMapper.toOrderResponse(order);
    }

    // ================= MY DELIVERIES =================
    @GetMapping("/my")
    public List<OrderResponseDTO> myDeliveries(Authentication auth) {
        return orderService.getOrdersForDelivery(auth.getName())
                .stream()
                .map(OrderMapper::toOrderResponse)
                .toList();
    }

    // ================= UPDATE STATUS =================
    @PutMapping("/{orderId}/status")
    public OrderResponseDTO updateStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status,
            Authentication auth) {

        Order order = orderService.updateDeliveryStatus(orderId, status, auth.getName());
        return OrderMapper.toOrderResponse(order);
    }
}