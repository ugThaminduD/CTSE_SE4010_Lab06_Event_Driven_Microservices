package com.example.inventory_service.event;

public class OrderCreatedEvent {

    private String orderId;
    private String item;
    private int quantity;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(String orderId, String item, int quantity) {
        this.orderId = orderId;
        this.item = item;
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
}
