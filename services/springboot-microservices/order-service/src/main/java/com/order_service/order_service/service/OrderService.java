package com.order_service.order_service.service;

import com.order_service.order_service.entity.Order;

public interface OrderService {
    Order placeOrder(Long productId, Integer quantity);
}
