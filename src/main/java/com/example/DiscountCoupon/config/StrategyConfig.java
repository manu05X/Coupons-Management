package com.example.DiscountCoupon.config;

import com.example.DiscountCoupon.model.CouponType;
import com.example.DiscountCoupon.strategy.BxGyCouponStrategy;
import com.example.DiscountCoupon.strategy.CartWiseCouponStrategy;
import com.example.DiscountCoupon.strategy.CouponStrategy;
import com.example.DiscountCoupon.strategy.ProductWiseCouponStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class StrategyConfig {
    @Bean
    public Map<CouponType, CouponStrategy> couponStrategyMap(
            CartWiseCouponStrategy cartWiseStrategy,
            ProductWiseCouponStrategy productWiseStrategy,
            BxGyCouponStrategy bxGyStrategy)
    {
        Map<CouponType, CouponStrategy> strategyMap = new HashMap<>();
        strategyMap.put(CouponType.CART_WISE, cartWiseStrategy);
        strategyMap.put(CouponType.PRODUCT_WISE, productWiseStrategy);
        strategyMap.put(CouponType.BUY_X_GET_Y, bxGyStrategy);
        return strategyMap;
    }
}