package com.example.inventory_service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryConsumer {

    @KafkaListener(topics = "order-topic", groupId = "inventory-group")
    public void consumeOrderEvent(String order) {
        // Simulating the stock update as per the lab requirements
        System.out.println("📦 Inventory Service received event: " + order);
        System.out.println("✅ Status: Stock updated successfully.");
    }
}