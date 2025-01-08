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
        // Map the CouponRequestDTO to Coupon entity
        Coupon coupon = couponMapper.toEntity(couponRequest);

        // Call the service layer to save the coupon
        Coupon savedCoupon = couponService.createCoupon(coupon);

        // Map the saved Coupon entity to CouponResponseDTO
        CouponResponseDTO responseDTO = couponMapper.toResponseDTO(savedCoupon);

        // Return the response with the CouponResponseDTO
        return ResponseEntity.ok(responseDTO);
    }


    @GetMapping
    public ResponseEntity<List<CouponResponseDTO>> getAllCoupons() {
        return ResponseEntity.ok(couponService.getAllCoupons());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> getCouponById(@PathVariable Long id) {
        // Call the service to get the Coupon entity by ID and map to DTO
        CouponResponseDTO responseDTO = couponService.getCouponById(id);

        // Return the CouponResponseDTO as a response
        return ResponseEntity.ok(responseDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> updateCoupon(@PathVariable Long id, @RequestBody CouponRequestDTO couponRequest) {
        // Map the incoming CouponRequestDTO to a Coupon entity
        Coupon coupon = couponMapper.toEntity(couponRequest);

        // Call the service to update the Coupon
        Coupon updatedCoupon = couponService.updateCoupon(id, coupon);

        // Map the updated Coupon entity to a CouponResponseDTO
        CouponResponseDTO responseDTO = couponMapper.toResponseDTO(updatedCoupon);

        // Return the updated CouponResponseDTO
        return ResponseEntity.ok(responseDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id); // Call the service to delete the coupon by ID
        return ResponseEntity.noContent().build(); // Return 204 No Content to indicate successful deletion
    }


    @PostMapping("/applicable-coupons")
    public ResponseEntity<List<ApplicableCouponResponseDTO>> getApplicableCoupons(@RequestBody CartDTO cartDTO) {
        Cart cart = cartMapper.toEntity(cartDTO);  // Convert CartDTO to Cart entity
        List<ApplicableCouponResponseDTO> applicableCoupons = couponService.getApplicableCoupons(cart);
        return ResponseEntity.ok(applicableCoupons);
    }


    @PostMapping("/apply")
    public ResponseEntity<CartDTO> applyCoupon(@RequestBody ApplyCouponRequestDTO requestDTO) {
        Cart cart = cartMapper.toEntity(requestDTO.getCart());  // Convert CartDTO to Cart entity
        Cart updatedCart = couponService.applyCoupon(requestDTO.getCouponCode(), cart);
        CartDTO updatedCartDTO = cartMapper.toDTO(updatedCart);  // Convert updated Cart entity back to CartDTO
        return ResponseEntity.ok(updatedCartDTO);
    }

}
