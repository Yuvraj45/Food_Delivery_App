package com.fooddelivery.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddelivery.demo.model.Order;
import com.fooddelivery.demo.model.OrderStatus;
import com.fooddelivery.demo.model.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // ================= CUSTOMER =================
    List<Order> findByCustomer(User customer);
    Page<Order> findByCustomer(User customer, Pageable pageable);

    // ================= DELIVERY =================
    List<Order> findByDeliveryPerson(User deliveryPerson);

    // ================= ADMIN (RESTAURANT) =================
    List<Order> findByAdmin(User admin);

    // ================= STATUS =================
    List<Order> findByStatus(OrderStatus status);
    
    
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    // ================= ALL =================
    Page<Order> findAll(Pageable pageable);
    
    List<Order> findByCustomer_Email(String email);
    
    

}