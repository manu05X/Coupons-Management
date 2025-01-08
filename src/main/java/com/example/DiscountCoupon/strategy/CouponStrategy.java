package com.example.DiscountCoupon.strategy;


import com.example.DiscountCoupon.model.Cart;
import com.example.DiscountCoupon.model.Coupon;

public interface CouponStrategy {
    double calculateDiscount(Coupon coupon, Cart cart);
    boolean isApplicable(Coupon coupon, Cart cart);
}