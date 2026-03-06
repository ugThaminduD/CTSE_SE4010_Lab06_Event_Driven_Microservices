package com.example.order_service.service;

import com.example.order_service.dto.OrderCreateRequest;
import com.example.order_service.event.OrderCreatedEvent;
import com.example.order_service.model.Order;
import com.example.order_service.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.kafka.order-topic}")
    private String orderTopic;

    public OrderService(
            OrderRepository orderRepository,
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public Order createOrder(OrderCreateRequest request) {
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setItem(request.getItem());
        order.setQuantity(request.getQuantity());

        Order saved = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                saved.getOrderId(),
                saved.getItem(),
                saved.getQuantity());

        try {
            String payload = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(orderTopic, saved.getOrderId(), payload);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize order event", e);
        }

        return saved;
    }
}
