package com.example.DiscountCoupon.service;

import com.example.DiscountCoupon.dto.ApplicableCouponResponseDTO;
import com.example.DiscountCoupon.dto.CouponResponseDTO;
import com.example.DiscountCoupon.exception.CouponNotFoundException;
import com.example.DiscountCoupon.exception.InvalidCouponException;
import com.example.DiscountCoupon.mapper.CouponMapper;
import com.example.DiscountCoupon.model.Cart;
import com.example.DiscountCoupon.model.Coupon;
import com.example.DiscountCoupon.model.CouponType;
import com.example.DiscountCoupon.repository.CouponRepository;
import com.example.DiscountCoupon.strategy.CouponStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private Map<CouponType, CouponStrategy> strategyMap;

    @Autowired
    private CouponMapper couponMapper;

    public Coupon createCoupon(Coupon coupon) {

        Coupon tempCoupon = new Coupon();
        tempCoupon.setType(coupon.getType());
        tempCoupon.setCode(coupon.getCode());
        tempCoupon.setDiscountValue(coupon.getDiscountValue());
        tempCoupon.setMinimumCartValue(coupon.getMinimumCartValue());
        tempCoupon.setExpiryDate(coupon.getExpiryDate());
        tempCoupon.setApplicableProductIds(coupon.getApplicableProductIds());
        tempCoupon.setBuyProductIds(coupon.getBuyProductIds());
        tempCoupon.setGetProductIds(coupon.getGetProductIds());
        tempCoupon.setBuyQuantity(coupon.getBuyQuantity());
        tempCoupon.setGetQuantity(coupon.getGetQuantity());
        tempCoupon.setRepetitionLimit(coupon.getRepetitionLimit());

        return couponRepository.save(tempCoupon);
    }


    public List<CouponResponseDTO> getAllCoupons() {
        List<Coupon> coupons = couponRepository.findAll();
        return coupons.stream()
                .map(coupon -> {
                    CouponResponseDTO responseDTO = new CouponResponseDTO();
                    responseDTO.setId(coupon.getId());
                    responseDTO.setType(coupon.getType().name());
                    responseDTO.setCode(coupon.getCode());
                    responseDTO.setDiscountValue(coupon.getDiscountValue());
                    responseDTO.setMinimumCartValue(coupon.getMinimumCartValue());
                    responseDTO.setExpiryDate(coupon.getExpiryDate());
                    responseDTO.setRepetitionLimit(coupon.getRepetitionLimit());
                    return responseDTO;
                })
                .collect(Collectors.toList());
    }


    public CouponResponseDTO getCouponById(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found with id: " + id));


        return couponMapper.toResponseDTO(coupon);
    }


    public Coupon updateCoupon(Long id, Coupon coupon) {
        if (!couponRepository.existsById(id)) {
            throw new CouponNotFoundException("Coupon not found with id: " + id);
        }

        coupon.setId(id);

        return couponRepository.save(coupon);
    }


    public void deleteCoupon(Long id) {
        if (!couponRepository.existsById(id)) {
            throw new CouponNotFoundException("Coupon not found with id: " + id); // Throw exception if coupon does not exist
        }
        couponRepository.deleteById(id);
    }


    public List<ApplicableCouponResponseDTO> getApplicableCoupons(Cart cart) {
        List<ApplicableCouponResponseDTO> applicableCoupons = new ArrayList<>();


        List<Coupon> coupons = getAllValidCoupons();
        System.out.println("All valid coupons: " + coupons);

        for (Coupon coupon : coupons) {
            CouponStrategy strategy = strategyMap.get(coupon.getType());

            if (strategy == null) {
                System.out.println("No strategy found for coupon type: " + coupon.getType());
                continue;
            }

            System.out.println("Checking coupon: " + coupon.getCode());
            boolean isApplicable = strategy.isApplicable(coupon, cart);
            System.out.println("Is applicable: " + isApplicable);

            if (isApplicable) {
                double discount = strategy.calculateDiscount(coupon, cart);
                ApplicableCouponResponseDTO responseDTO = new ApplicableCouponResponseDTO();
                responseDTO.setCouponCode(coupon.getCode());
                responseDTO.setDiscount(discount);
                applicableCoupons.add(responseDTO);
            }
        }

        System.out.println("Applicable coupons: " + applicableCoupons);
        return applicableCoupons;
    }



    public Cart applyCoupon(String couponCode, Cart cart) {
        Coupon coupon = couponRepository.findByCode(couponCode)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found with code: " + couponCode));

        if (coupon.getExpiryDate() != null && coupon.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidCouponException("Coupon has expired");
        }

        CouponStrategy strategy = strategyMap.get(coupon.getType());
        if (!strategy.isApplicable(coupon, cart)) {
            throw new InvalidCouponException("Coupon is not applicable to this cart");
        }

        double discount = strategy.calculateDiscount(coupon, cart);
        cart.setTotalAmount(cart.getTotalAmount() - discount);

        return cart;
    }

    public List<Coupon> getAllValidCoupons() {
        return couponRepository.findByExpiryDateAfterOrExpiryDateIsNull(LocalDateTime.now());
    }


}
