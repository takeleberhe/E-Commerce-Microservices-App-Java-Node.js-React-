package com.order_service.order_service.service;

import com.order_service.order_service.dto.ProductResponse;
import com.order_service.order_service.entity.Order;
import com.order_service.order_service.kafka.OrderEvent;
import com.order_service.order_service.kafka.OrderProducer;
import com.order_service.order_service.repository.OrderRepository;
import com.order_service.order_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProducer orderProducer; // Inject Kafka producer

    @Override
    public Order placeOrder(Long productId, Integer quantity) {
        log.info("📦 Fetching product info from Product Service for productId {}", productId);

        ProductResponse product = productRepository.getProductById(productId);

        if (product == null) {
            log.error("Product not found: {}", productId);
            throw new RuntimeException("Product not found");
        }

        if (product.getStock() < quantity) {
            log.error("Insufficient stock for product ID {}. Requested: {}, Available: {}",
                    productId, quantity, product.getStock());
            throw new RuntimeException("Insufficient stock");
        }

        double totalAmount = product.getPrice() * quantity;

        Order order = Order.builder()
                .productId(productId)
                .quantity(quantity)
                .price(product.getPrice())
                .totalAmount(totalAmount)
                .build();

        Order savedOrder = orderRepository.save(order);

        log.info(" Order placed successfully. Order ID: {}", savedOrder.getId());

        // Send order event to Kafka
        OrderEvent event = OrderEvent.builder()
                .orderId(savedOrder.getId())
                .productId(savedOrder.getProductId())
                .quantity(savedOrder.getQuantity())
                .totalAmount(savedOrder.getTotalAmount())
                .status("PLACED")
                .build();

        orderProducer.sendOrderEvent(event); // Produce event to Kafka topic

        return savedOrder;
    }
}
