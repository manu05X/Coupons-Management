package com.example.DiscountCoupon.strategy;

import com.example.DiscountCoupon.model.Cart;
import com.example.DiscountCoupon.model.Coupon;
import org.springframework.stereotype.Component;

@Component
public class ProductWiseCouponStrategy implements CouponStrategy {
    @Override
    public double calculateDiscount(Coupon coupon, Cart cart) {
        return cart.getItems().stream()
            .filter(item -> coupon.getApplicableProductIds().contains(item.getProductId()))
            .mapToDouble(item -> item.getPrice() * item.getQuantity() * (coupon.getDiscountValue() / 100))
            .sum();
    }

    @Override
    public boolean isApplicable(Coupon coupon, Cart cart) {
        return cart.getItems().stream()
            .anyMatch(item -> coupon.getApplicableProductIds().contains(item.getProductId()));
    }
}