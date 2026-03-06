package com.example.inventory_service.service;

import com.example.inventory_service.event.OrderCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryEventListener {

    private static final Logger log = LoggerFactory.getLogger(InventoryEventListener.class);
    private final ObjectMapper objectMapper;

    public InventoryEventListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${app.kafka.order-topic}", groupId = "inventory-service")
    public void onOrderCreated(String payload) {
        try {
            OrderCreatedEvent event = objectMapper.readValue(payload, OrderCreatedEvent.class);
            log.info("Inventory updated for orderId={}, item={}, qty={}",
                    event.getOrderId(), event.getItem(), event.getQuantity());
        } catch (Exception e) {
            log.error("Failed to process inventory event: {}", payload, e);
        }
    }
}
