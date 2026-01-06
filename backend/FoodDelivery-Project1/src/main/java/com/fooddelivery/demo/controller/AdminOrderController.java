package com.fooddelivery.demo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.demo.dto.mapper.AdminOrderMapper;
import com.fooddelivery.demo.dto.response.AdminOrderResponseDTO;
import com.fooddelivery.demo.model.Order;
import com.fooddelivery.demo.model.OrderStatus;
import com.fooddelivery.demo.model.User;
import com.fooddelivery.demo.repository.UserRepository;
import com.fooddelivery.demo.service.OrderService;

@RestController
@RequestMapping("/admin/orders")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*")
public class AdminOrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    public AdminOrderController(
            OrderService orderService,
            UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    // ================= ADMIN → VIEW ORDERS =================
    @GetMapping
    public List<AdminOrderResponseDTO> getAdminOrders(Authentication auth) {

        User admin = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        List<Order> orders = orderService.getOrdersByAdmin(admin);

        return orders.stream()
                .map(AdminOrderMapper::toAdminOrderResponse)
                .toList();
    }

    // ================= ADMIN → REQUEST DELIVERY =================
    @PutMapping("/{orderId}/request-delivery")
    public AdminOrderResponseDTO requestDelivery(@PathVariable Long orderId) {

        Order order = orderService.markOrderForDelivery(orderId);
        return AdminOrderMapper.toAdminOrderResponse(order);
    }

    // ================= ADMIN → UPDATE STATUS =================
    @PutMapping("/{orderId}/status")
    public AdminOrderResponseDTO updateStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {

        Order order = orderService.updateStatusByAdmin(orderId, status);
        return AdminOrderMapper.toAdminOrderResponse(order);
    }
 // ================= ADMIN → TOTAL REVENUE =================
    @GetMapping("/revenue")
    public Double getTotalRevenue(Authentication auth) {
        return orderService.calculateAdminRevenue(auth.getName());
    }

}