package com.fooddelivery.demo.dto.response;

import java.util.List;

import com.fooddelivery.demo.model.OrderStatus;
import com.fooddelivery.demo.model.PaymentMode;
import com.fooddelivery.demo.model.PaymentStatus;

public class OrderResponseDTO {

    private Long id;
    private UserSummaryDTO customer;
    private UserSummaryDTO deliveryPerson;
    private OrderStatus status;
    private PaymentMode paymentMode;
    private PaymentStatus paymentStatus;
    private Double totalAmount;
    private List<OrderItemDTO> items;

    public OrderResponseDTO(
            Long id,
            UserSummaryDTO customer,
            UserSummaryDTO deliveryPerson,
            OrderStatus status,
            PaymentMode paymentMode,
            PaymentStatus paymentStatus,
            Double totalAmount,
            List<OrderItemDTO> items) {

        this.id = id;
        this.customer = customer;
        this.deliveryPerson = deliveryPerson;
        this.status = status;
        this.paymentMode = paymentMode;
        this.paymentStatus = paymentStatus;
        this.totalAmount = totalAmount;
        this.items = items;
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

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }
}