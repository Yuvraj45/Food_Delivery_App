package com.fooddelivery.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fooddelivery.demo.model.*;
import com.fooddelivery.demo.repository.*;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public OrderService(
            OrderRepository orderRepository,
            CartItemRepository cartItemRepository,
            UserRepository userRepository
    ) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    // =====================================================
    // USER → PLACE ORDER FROM CART
    // =====================================================
    public Order placeOrderFromCart(String email, Long adminId, PaymentMode paymentMode) {

        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        List<CartItem> cartItems = cartItemRepository.findByCustomer(customer);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setAdmin(admin);
        order.setStatus(OrderStatus.PLACED);
        order.setPaymentMode(paymentMode);
        order.setPaymentStatus(PaymentStatus.PENDING);

        double totalAmount = 0;

        for (CartItem cartItem : cartItems) {

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setFoodItem(cartItem.getFoodItem());
            orderItem.setQuantity(cartItem.getQuantity());

            double itemTotal =
                    cartItem.getFoodItem().getPrice() * cartItem.getQuantity();

            orderItem.setPrice(itemTotal);
            totalAmount += itemTotal;

            order.getItems().add(orderItem); // cascade save
        }

        // ✅ CRITICAL FIX
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        // clear cart
        cartItemRepository.deleteByCustomer(customer);

        return savedOrder;
    }

    // =====================================================
    // USER → VIEW MY ORDERS
    // =====================================================
    public List<Order> getMyOrders(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByCustomer(user);
    }

    // =====================================================
    // USER → FAKE PAYMENT UPDATE
    // =====================================================
    public Order updatePaymentStatus(Long orderId, PaymentStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setPaymentStatus(status);

        if (status == PaymentStatus.PAID) {
            order.setStatus(OrderStatus.CONFIRMED);
        }

        if (status == PaymentStatus.FAILED) {
            order.setStatus(OrderStatus.PLACED);
        }

        return orderRepository.save(order);
    }

    // =====================================================
    // ADMIN → VIEW ORDERS
    // =====================================================
    public List<Order> getOrdersByAdmin(User admin) {
        return orderRepository.findByAdmin(admin);
    }

    // =====================================================
    // ADMIN → REQUEST DELIVERY
    // =====================================================
    public Order markOrderForDelivery(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.DELIVERY_REQUESTED);
        return orderRepository.save(order);
    }

    // =====================================================
    // ADMIN → ASSIGN DELIVERY
    // =====================================================
    public Order assignDelivery(Long orderId, User deliveryPerson) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setDeliveryPerson(deliveryPerson);
        order.setStatus(OrderStatus.ASSIGNED);

        return orderRepository.save(order);
    }

    // =====================================================
    // ADMIN → UPDATE STATUS
    // =====================================================
    public Order updateStatusByAdmin(Long orderId, OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        return orderRepository.save(order);
    }

    // =====================================================
    // ADMIN → TOTAL REVENUE
    // =====================================================
    public Double calculateAdminRevenue(String email) {

        User admin = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        return orderRepository.findByAdmin(admin)
                .stream()
                .filter(order ->
                        (order.getPaymentMode() != PaymentMode.COD &&
                         order.getPaymentStatus() == PaymentStatus.PAID)
                        ||
                        (order.getPaymentMode() == PaymentMode.COD &&
                         order.getStatus() == OrderStatus.DELIVERED)
                )
                .mapToDouble(Order::getTotalAmount)
                .sum();
    }

    // =====================================================
    // DELIVERY → VIEW DELIVERY REQUESTS
    // =====================================================
    public List<Order> getDeliveryRequests() {
        return orderRepository.findByStatus(OrderStatus.DELIVERY_REQUESTED);
    }

    // =====================================================
    // DELIVERY → ACCEPT DELIVERY
    // =====================================================
    public Order acceptDelivery(Long orderId, String email) {

        User deliveryPerson = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Delivery user not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.DELIVERY_REQUESTED) {
            throw new RuntimeException("Order not available for delivery");
        }

        order.setDeliveryPerson(deliveryPerson);
        order.setStatus(OrderStatus.ASSIGNED);

        return orderRepository.save(order);
    }

    // =====================================================
    // DELIVERY → MY ORDERS
    // =====================================================
    public List<Order> getOrdersForDelivery(String email) {

        User deliveryPerson = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Delivery user not found"));

        return orderRepository.findByDeliveryPerson(deliveryPerson);
    }

    // =====================================================
    // DELIVERY → UPDATE STATUS
    // =====================================================
    public Order updateDeliveryStatus(
            Long orderId,
            OrderStatus status,
            String email) {

        User deliveryPerson = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Delivery user not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getDeliveryPerson() == null ||
            !order.getDeliveryPerson().getId().equals(deliveryPerson.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        order.setStatus(status);
        return orderRepository.save(order);
    }

    // =====================================================
    // DELIVERY → LIVE LOCATION UPDATE
    // =====================================================
    public Order updateDeliveryLocation(
            Long orderId,
            Double lat,
            Double lng,
            String email) {

        User deliveryPerson = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Delivery user not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getDeliveryPerson() == null ||
            !order.getDeliveryPerson().getId().equals(deliveryPerson.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        order.setDeliveryLat(lat);
        order.setDeliveryLng(lng);

        return orderRepository.save(order);
    }
}