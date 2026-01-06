package com.fooddelivery.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "food_items")
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ---------------- BASIC INFO ----------------

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category; 
    // Examples: Biryani, Burger, Pizza, Fries, Shawarma, etc.

    @Column(nullable = false)
    private String type; 
    // VEG or NON_VEG

    private String description;

    private String imageUrl;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private boolean available = true;

    // ---------------- RELATIONSHIP ----------------

    // ADMIN = RESTAURANT OWNER
    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    // ---------------- GETTERS & SETTERS ----------------

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }
}