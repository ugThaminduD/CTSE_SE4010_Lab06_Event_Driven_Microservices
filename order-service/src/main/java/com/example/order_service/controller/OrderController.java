package com.example.order_service.controller;

import com.example.order_service.dto.OrderCreateRequest;
import com.example.order_service.model.Order;
import com.example.order_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@RequestBody OrderCreateRequest request) {
        return orderService.createOrder(request);
    }
}
