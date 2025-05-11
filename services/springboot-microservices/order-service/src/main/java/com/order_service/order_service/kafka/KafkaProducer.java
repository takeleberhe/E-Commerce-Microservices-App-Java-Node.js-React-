package com.order_service.order_service.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static KafkaTemplate<String, String> kafkaTemplate = null;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Send the 'order-created' message to Kafka
    public static void sendOrderCreatedMessage(String orderDetails) {
        // Sending the message to the 'order-created' Kafka topic
        ProducerRecord<String, String> record = new ProducerRecord<>("order-created", orderDetails);
        kafkaTemplate.send(record);  // Send message to Kafka
        System.out.println("Order created event sent to Kafka: " + orderDetails);
    }
}

