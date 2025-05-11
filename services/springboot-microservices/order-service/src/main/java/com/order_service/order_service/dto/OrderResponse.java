package com.order_service.order_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Double price;
    private Double totalAmount;
}
