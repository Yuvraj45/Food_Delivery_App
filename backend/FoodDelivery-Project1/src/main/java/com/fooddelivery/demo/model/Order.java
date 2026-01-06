package com.fooddelivery.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // CUSTOMER
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    // ADMIN / RESTAURANT
    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    // DELIVERY PERSON
    @ManyToOne
    @JoinColumn(name = "delivery_person_id")
    private User deliveryPerson;

    @OneToMany(
        mappedBy = "order",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private List<OrderItem> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private Double totalAmount;

    // 🔴 LIVE TRACKING
    private Double deliveryLat;
    private Double deliveryLng;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // ===== GETTERS & SETTERS =====

    public Long getId() { return id; }

    public User getCustomer() { return customer; }
    public void setCustomer(User customer) { this.customer = customer; }

    public User getAdmin() { return admin; }
    public void setAdmin(User admin) { this.admin = admin; }

    public User getDeliveryPerson() { return deliveryPerson; }
    public void setDeliveryPerson(User deliveryPerson) { this.deliveryPerson = deliveryPerson; }

    public List<OrderItem> getItems() { return items; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public PaymentMode getPaymentMode() { return paymentMode; }
    public void setPaymentMode(PaymentMode paymentMode) { this.paymentMode = paymentMode; }

    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public Double getDeliveryLat() { return deliveryLat; }
    public void setDeliveryLat(Double deliveryLat) { this.deliveryLat = deliveryLat; }

    public Double getDeliveryLng() { return deliveryLng; }
    public void setDeliveryLng(Double deliveryLng) { this.deliveryLng = deliveryLng; }
}