package com.fooddelivery.demo.dto.mapper;

import com.fooddelivery.demo.dto.response.AdminOrderResponseDTO;
import com.fooddelivery.demo.dto.response.UserSummaryDTO;
import com.fooddelivery.demo.model.Order;

public class AdminOrderMapper {

    public static AdminOrderResponseDTO toAdminOrderResponse(Order order) {

        return new AdminOrderResponseDTO(
                order.getId(),   // ✅ ORDER ID
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
                order.getTotalAmount()
        );
    }
}