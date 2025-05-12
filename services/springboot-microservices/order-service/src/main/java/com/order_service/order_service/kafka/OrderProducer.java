package com.order_service.order_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${app.kafka.order-topic}")
    private String orderTopic;

    public void sendOrderEvent(OrderEvent orderEvent) {
        kafkaTemplate.send(orderTopic, orderEvent);
        log.info("Sent Kafka order event to topic [{}]: {}", orderTopic, orderEvent);
    }
}
