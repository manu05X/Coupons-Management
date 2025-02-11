package com.example.DiscountCoupon.controller;

import com.example.DiscountCoupon.dto.*;
import com.example.DiscountCoupon.mapper.CartMapper;
import com.example.DiscountCoupon.mapper.CouponMapper;
import com.example.DiscountCoupon.model.Cart;
import com.example.DiscountCoupon.model.Coupon;
import com.example.DiscountCoupon.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CartMapper cartMapper;


    @PostMapping("/create")
    public ResponseEntity<CouponResponseDTO> createCoupon(@RequestBody CouponRequestDTO couponRequest) {
        Coupon coupon = couponMapper.toEntity(couponRequest);
        Coupon savedCoupon = couponService.createCoupon(coupon);
        CouponResponseDTO responseDTO = couponMapper.toResponseDTO(savedCoupon);
        return ResponseEntity.ok(responseDTO);
    }


    @GetMapping
    public ResponseEntity<List<CouponResponseDTO>> getAllCoupons() {
        return ResponseEntity.ok(couponService.getAllCoupons());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> getCouponById(@PathVariable Long id) {
        CouponResponseDTO responseDTO = couponService.getCouponById(id);
        return ResponseEntity.ok(responseDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> updateCoupon(@PathVariable Long id, @RequestBody CouponRequestDTO couponRequest) {
        Coupon coupon = couponMapper.toEntity(couponRequest);
        Coupon updatedCoupon = couponService.updateCoupon(id, coupon);
        CouponResponseDTO responseDTO = couponMapper.toResponseDTO(updatedCoupon);

        return ResponseEntity.ok(responseDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/applicable-coupons")
    public ResponseEntity<List<ApplicableCouponResponseDTO>> getApplicableCoupons(@RequestBody CartDTO cartDTO) {
        Cart cart = cartMapper.toEntity(cartDTO);
        List<ApplicableCouponResponseDTO> applicableCoupons = couponService.getApplicableCoupons(cart);
        return ResponseEntity.ok(applicableCoupons);
    }


    @PostMapping("/apply")
    public ResponseEntity<CartDTO> applyCoupon(@RequestBody ApplyCouponRequestDTO requestDTO) {
        Cart cart = cartMapper.toEntity(requestDTO.getCart());
        Cart updatedCart = couponService.applyCoupon(requestDTO.getCouponCode(), cart);
        CartDTO updatedCartDTO = cartMapper.toDTO(updatedCart);
        return ResponseEntity.ok(updatedCartDTO);
    }

}
