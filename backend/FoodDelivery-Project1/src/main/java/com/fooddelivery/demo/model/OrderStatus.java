package com.fooddelivery.demo.model;

public enum OrderStatus {
    PLACED,
    CONFIRMED,          // ✅ ADD THIS
    DELIVERY_REQUESTED,
    ASSIGNED,
    PICKED_UP,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}