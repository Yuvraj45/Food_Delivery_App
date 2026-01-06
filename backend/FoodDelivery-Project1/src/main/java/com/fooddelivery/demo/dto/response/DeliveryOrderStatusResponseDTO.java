package com.fooddelivery.demo.dto.response;

import com.fooddelivery.demo.model.OrderStatus;

public class DeliveryOrderStatusResponseDTO {

    private Long orderId;
    private OrderStatus status;

    public DeliveryOrderStatusResponseDTO(Long orderId, OrderStatus status) {
        this.orderId = orderId;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }
}