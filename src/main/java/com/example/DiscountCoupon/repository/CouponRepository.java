package com.example.DiscountCoupon.repository;

import com.example.DiscountCoupon.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findByCode(String code);
    List<Coupon> findByExpiryDateAfterOrExpiryDateIsNull(LocalDateTime now);
}