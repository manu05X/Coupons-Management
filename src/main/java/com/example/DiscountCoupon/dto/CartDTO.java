package com.example.DiscountCoupon.dto;

import java.util.List;

public class CartDTO {
    private List<CartItemDTO> items;
    private Double totalAmount;

    // Default constructor
    public CartDTO() {}

    public List<CartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
