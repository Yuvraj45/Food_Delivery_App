package com.fooddelivery.demo.dto.response;

public class OrderItemDTO {

    private Long foodItemId;
    private String foodName;
    private int quantity;
    private double price;

    public OrderItemDTO(Long foodItemId, String foodName, int quantity, double price) {
        this.foodItemId = foodItemId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getFoodItemId() {
        return foodItemId;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}