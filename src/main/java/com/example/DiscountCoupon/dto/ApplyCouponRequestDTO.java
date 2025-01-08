package com.example.DiscountCoupon.dto;

public class ApplyCouponRequestDTO {
    private CartDTO cart;
    private String couponCode;

    // Default constructor
    public ApplyCouponRequestDTO() {}

    public CartDTO getCart() {
        return cart;
    }

    public void setCart(CartDTO cart) {
        this.cart = cart;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
}
