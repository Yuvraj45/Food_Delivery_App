package com.fooddelivery.demo.dto.response;

import com.fooddelivery.demo.model.OrderStatus;

public class AdminOrderResponseDTO {

    private Long id;   // ✅ FIXED
    private UserSummaryDTO customer;
    private UserSummaryDTO deliveryPerson;
    private OrderStatus status;
    private Double totalAmount;

    public AdminOrderResponseDTO(
            Long id,
            UserSummaryDTO customer,
            UserSummaryDTO deliveryPerson,
            OrderStatus status,
            Double totalAmount) {

        this.id = id;
        this.customer = customer;
        this.deliveryPerson = deliveryPerson;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public UserSummaryDTO getCustomer() {
        return customer;
    }

    public UserSummaryDTO getDeliveryPerson() {
        return deliveryPerson;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }
}