package com.example.DiscountCoupon.dto;

public class ApplicableCouponResponseDTO {
    private String couponCode;
    private Double discount;

    // Default constructor
    public ApplicableCouponResponseDTO() {}

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
