package com.example.billing_service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BillingConsumer {

    @KafkaListener(topics = "order-topic", groupId = "billing-group")
    public void consumeOrderEvent(String order) {
        // Simulating invoice generation as per the lab requirements
        System.out.println("💳 Billing Service received event: " + order);
        System.out.println("✅ Status: Invoice generated successfully.");
    }
}