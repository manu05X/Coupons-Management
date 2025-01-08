package com.example.DiscountCoupon.strategy;

import com.example.DiscountCoupon.model.Cart;
import com.example.DiscountCoupon.model.CartItem;
import com.example.DiscountCoupon.model.Coupon;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BxGyCouponStrategy implements CouponStrategy {
    @Override
    public double calculateDiscount(Coupon coupon, Cart cart) {
        if (!isApplicable(coupon, cart)) {
            return 0.0;
        }

        long buyProductCount = getBuyProductCount(coupon, cart);
        List<CartItem> getProducts = getEligibleGetProducts(coupon, cart);

        int maxApplications = calculateMaxApplications(coupon, buyProductCount);
        return calculateTotalDiscount(coupon, getProducts, maxApplications);
    }

    @Override
    public boolean isApplicable(Coupon coupon, Cart cart) {

        if (coupon.getBuyProductIds() == null || coupon.getBuyProductIds().isEmpty() ||
                coupon.getGetProductIds() == null || coupon.getGetProductIds().isEmpty() ||
                coupon.getBuyQuantity() <= 0 || coupon.getGetQuantity() <= 0) {
            return false;
        }

        long buyProductCount = getBuyProductCount(coupon, cart);
        long getProductCount = getGetProductCount(coupon, cart);

        return buyProductCount >= coupon.getBuyQuantity() &&
                getProductCount >= coupon.getGetQuantity();
    }

    private long getBuyProductCount(Coupon coupon, Cart cart) {
        return cart.getItems().stream()
                .filter(item -> coupon.getBuyProductIds().contains(item.getProductId()))
                .mapToLong(CartItem::getQuantity)
                .sum();
    }

    private long getGetProductCount(Coupon coupon, Cart cart) {
        return cart.getItems().stream()
                .filter(item -> coupon.getGetProductIds().contains(item.getProductId()))
                .mapToLong(CartItem::getQuantity)
                .sum();
    }

    private List<CartItem> getEligibleGetProducts(Coupon coupon, Cart cart) {
        return cart.getItems().stream()
                .filter(item -> coupon.getGetProductIds().contains(item.getProductId()))
                .sorted(Comparator.comparing(CartItem::getPrice))
                .collect(Collectors.toList());
    }

    private int calculateMaxApplications(Coupon coupon, long buyProductCount) {
        int maxApplications = (int) (buyProductCount / coupon.getBuyQuantity());
        if (coupon.getRepetitionLimit() != null) {
            maxApplications = Math.min(maxApplications, coupon.getRepetitionLimit());
        }
        return maxApplications;
    }

    private double calculateTotalDiscount(Coupon coupon, List<CartItem> getProducts, int maxApplications) {
        double totalDiscount = 0.0;
        int remainingApplications = maxApplications;
        int itemsUsedInCurrentApplication = 0;

        for (CartItem item : getProducts) {
            if (remainingApplications <= 0) break;

            int remainingItemQuantity = item.getQuantity();

            while (remainingItemQuantity > 0 && remainingApplications > 0) {

                int itemsToUse = Math.min(
                        remainingItemQuantity,
                        coupon.getGetQuantity() - itemsUsedInCurrentApplication
                );


                double discountRate = (coupon.getDiscountPercentage() != null)
                        ? coupon.getDiscountPercentage() / 100.0
                        : 1.0;

                double itemDiscount = item.getPrice() * itemsToUse * discountRate;

                totalDiscount += itemDiscount;
                remainingItemQuantity -= itemsToUse;
                itemsUsedInCurrentApplication += itemsToUse;

                if (itemsUsedInCurrentApplication >= coupon.getGetQuantity()) {
                    remainingApplications--;
                    itemsUsedInCurrentApplication = 0;
                }
            }
        }

        return totalDiscount;
    }
}
