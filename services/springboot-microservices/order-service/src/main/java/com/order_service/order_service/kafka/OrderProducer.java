package com.order_service.order_service.kafka;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendOrderEvent(OrderEvent orderEvent) {
        String message = orderEvent.toString(); // for now, send simple string message
        kafkaTemplate.send("order-events", message);
        log.info(" Sent Kafka order event: {}", message);
    }
}
