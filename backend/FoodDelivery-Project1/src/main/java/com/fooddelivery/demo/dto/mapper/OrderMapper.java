package com.fooddelivery.demo.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.fooddelivery.demo.dto.response.OrderItemDTO;
import com.fooddelivery.demo.dto.response.OrderResponseDTO;
import com.fooddelivery.demo.dto.response.UserSummaryDTO;
import com.fooddelivery.demo.model.Order;
import com.fooddelivery.demo.model.OrderItem;

public class OrderMapper {

    public static OrderResponseDTO toOrderResponse(Order order) {

        List<OrderItemDTO> items =
                order.getItems()
                     .stream()
                     .map(OrderMapper::toOrderItemDTO)
                     .collect(Collectors.toList());

        return new OrderResponseDTO(
                order.getId(),              // ✅ ORDER ID (FIXED)
                new UserSummaryDTO(
                        order.getCustomer().getId(),
                        order.getCustomer().getName(),
                        order.getCustomer().getEmail(),
                        order.getCustomer().getRole()
                ),
                order.getDeliveryPerson() == null
                        ? null
                        : new UserSummaryDTO(
                            order.getDeliveryPerson().getId(),
                            order.getDeliveryPerson().getName(),
                            order.getDeliveryPerson().getEmail(),
                            order.getDeliveryPerson().getRole()
                        ),
                order.getStatus(),
                order.getPaymentMode(),
                order.getPaymentStatus(),
                order.getTotalAmount(),
                items
        );
    }

    private static OrderItemDTO toOrderItemDTO(OrderItem item) {
        return new OrderItemDTO(
                item.getFoodItem().getId(),
                item.getFoodItem().getName(),
                item.getQuantity(),
                item.getPrice()
        );
    }
}