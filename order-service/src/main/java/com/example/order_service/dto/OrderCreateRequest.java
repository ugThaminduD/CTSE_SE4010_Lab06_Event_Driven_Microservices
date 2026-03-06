package com.example.order_service.dto;

public class OrderCreateRequest {

    private String item;
    private int quantity;

    public OrderCreateRequest() {
    }

    public OrderCreateRequest(String item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
