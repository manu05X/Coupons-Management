package com.example.DiscountCoupon.model;

import lombok.Data;

import java.util.List;

@Data
public class Cart {
    private List<CartItem> items;
    private Double totalAmount;

    public Cart(){}

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}