package com.example.DiscountCoupon.strategy;

import com.example.DiscountCoupon.model.Cart;
import com.example.DiscountCoupon.model.Coupon;
import org.springframework.stereotype.Component;

@Component
public class CartWiseCouponStrategy implements CouponStrategy {
    @Override
    public double calculateDiscount(Coupon coupon, Cart cart) {
        if (!isApplicable(coupon, cart)) {
            return 0.0;
        }
        return cart.getTotalAmount() * (coupon.getDiscountValue() / 100);
    }

    @Override
    public boolean isApplicable(Coupon coupon, Cart cart) {
        return cart.getTotalAmount() >= coupon.getMinimumCartValue();
    }
}