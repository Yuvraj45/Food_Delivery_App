package com.fooddelivery.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddelivery.demo.model.Order;
import com.fooddelivery.demo.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Get all items for a given order
    List<OrderItem> findByOrder(Order order);
}