package com.example.billing_service.service;

import com.example.billing_service.event.OrderCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BillingEventListener {

    private static final Logger log = LoggerFactory.getLogger(BillingEventListener.class);
    private final ObjectMapper objectMapper;

    public BillingEventListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${app.kafka.order-topic}", groupId = "billing-service")
    public void onOrderCreated(String payload) {
        try {
            OrderCreatedEvent event = objectMapper.readValue(payload, OrderCreatedEvent.class);
            log.info("Invoice generated for orderId={}, item={}, qty={}",
                    event.getOrderId(), event.getItem(), event.getQuantity());
        } catch (Exception e) {
            log.error("Failed to process billing event: {}", payload, e);
        }
    }
}
