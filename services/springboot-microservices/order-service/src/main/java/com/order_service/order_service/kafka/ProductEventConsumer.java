package com.order_service.order_service.kafka;

import com.order_service.order_service.dto.ProductEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductEventConsumer {

    @KafkaListener(topics = "product-events", groupId = "order-service-group")
    public void consumeProductEvent(ProductEvent event) {
        log.info("Received product event in Order Service: {}", event);
    }
}
