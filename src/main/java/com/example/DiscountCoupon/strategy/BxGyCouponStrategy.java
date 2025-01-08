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
        // Validate coupon data
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
                .sorted(Comparator.comparing(CartItem::getPrice))  // Sort by price to maximize discount
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
                // Calculate how many items we can use in this application
                int itemsToUse = Math.min(
                        remainingItemQuantity,
                        coupon.getGetQuantity() - itemsUsedInCurrentApplication
                );

                // Calculate discount for these items
                double discountRate = (coupon.getDiscountPercentage() != null)
                        ? coupon.getDiscountPercentage() / 100.0
                        : 1.0;  // Default to 100% discount if no percentage specified

                double itemDiscount = item.getPrice() * itemsToUse * discountRate;

                totalDiscount += itemDiscount;
                remainingItemQuantity -= itemsToUse;
                itemsUsedInCurrentApplication += itemsToUse;

                // Check if we've completed an application
                if (itemsUsedInCurrentApplication >= coupon.getGetQuantity()) {
                    remainingApplications--;
                    itemsUsedInCurrentApplication = 0;
                }
            }
        }

        return totalDiscount;
    }
}



//public class BxGyCouponStrategy implements CouponStrategy {
//    @Override
//    public double calculateDiscount(Coupon coupon, Cart cart) {
//        if (!isApplicable(coupon, cart)) {
//            return 0.0;
//        }
//
//        long buyProductCount = getBuyProductCount(coupon, cart);
//        List<CartItem> getProducts = getEligibleGetProducts(coupon, cart);
//
//        int maxApplications = calculateMaxApplications(coupon, buyProductCount);
//        return calculateTotalDiscount(coupon, getProducts, maxApplications);
//    }
//
//    // The cart contains at least the specified number of products from the "buy" array (buyQuantity).
//    @Override
//    public boolean isApplicable(Coupon coupon, Cart cart) {
//        long buyProductCount = getBuyProductCount(coupon, cart);
//        long getProductCount = getGetProductCount(coupon, cart);
//
//        return buyProductCount >= coupon.getBuyQuantity() && getProductCount > 0;
//    }
//
//    // Calculate the total quantity of products in the cart from the "buy" array
//    private long getBuyProductCount(Coupon coupon, Cart cart) {
//        return cart.getItems().stream()
//            .filter(item -> coupon.getBuyProductIds().contains(item.getProductId()))
//            .mapToLong(CartItem::getQuantity)
//            .sum();
//    }
//
//    // Calculate the total quantity of products in the cart from the "get" array.
//    private long getGetProductCount(Coupon coupon, Cart cart) {
//        return cart.getItems().stream()
//            .filter(item -> coupon.getGetProductIds().contains(item.getProductId()))
//            .mapToLong(CartItem::getQuantity)
//            .sum();
//    }
//
//    // Ensure the cheapest eligible products from the "get" array are selected first to maximize the customer's discount.
//    private List<CartItem> getEligibleGetProducts(Coupon coupon, Cart cart) {
//        return cart.getItems().stream()
//            .filter(item -> coupon.getGetProductIds().contains(item.getProductId()))
//            .sorted(Comparator.comparing(CartItem::getPrice))
//            .collect(Collectors.toList());
//    }
//
//    //  how many times the coupon can be applied
//    private int calculateMaxApplications(Coupon coupon, long buyProductCount) {
//        int maxApplications = (int) (buyProductCount / coupon.getBuyQuantity());
//        if (coupon.getRepetitionLimit() != null) {
//            maxApplications = Math.min(maxApplications, coupon.getRepetitionLimit());
//        }
//        return maxApplications;
//    }
//
//    // Calculating the Total Discount
//    private double calculateTotalDiscount(Coupon coupon, List<CartItem> getProducts, int maxApplications) {
//        double totalDiscount = 0.0;
//        int remainingApplications = maxApplications;
//
//        for (CartItem item : getProducts) {
//            if (remainingApplications <= 0) break;
//
//            int freeQuantity = Math.min(
//                item.getQuantity(),
//                remainingApplications * coupon.getGetQuantity()
//            );
//            totalDiscount += item.getPrice() * freeQuantity;
//            remainingApplications -= (int) Math.ceil(freeQuantity / (double) coupon.getGetQuantity());
//        }
//
//        return totalDiscount;
//    }
//}