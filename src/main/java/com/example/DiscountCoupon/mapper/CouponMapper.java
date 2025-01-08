package com.example.DiscountCoupon.mapper;

import com.example.DiscountCoupon.dto.CouponRequestDTO;
import com.example.DiscountCoupon.dto.CouponResponseDTO;
import com.example.DiscountCoupon.model.Coupon;
import com.example.DiscountCoupon.model.CouponType;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {
//    public Coupon toEntity(CouponDTO request) {
//        Coupon coupon = new Coupon();
//
//        coupon.setCode(request.getCode());
//        coupon.setType(parseCouponType(request.getType()));
//        coupon.setDiscountValue(request.getDiscountValue());
//        coupon.setMinimumCartValue(request.getMinimumCartValue());
//        coupon.setExpiryDate(request.getExpiryDate());
////        coupon.setApplicableProductIds(request.getApplicableProductIds());
////        coupon.setBuyProductIds(request.getBuyProductIds());
////        coupon.setGetProductIds(request.getGetProductIds());
////        coupon.setBuyQuantity(request.getBuyQuantity());
////        coupon.setGetQuantity(request.getGetQuantity());
//        coupon.setRepetitionLimit(request.getRepetitionLimit());
//        return coupon;
//    }

    // Map from CouponRequestDTO to Coupon entity
    public Coupon toEntity(CouponRequestDTO dto) {
        Coupon coupon = new Coupon();
        coupon.setType(CouponType.valueOf(dto.getType())); // Convert String to Enum
        coupon.setCode(dto.getCode());
        coupon.setDiscountValue(dto.getDiscountValue());
        coupon.setMinimumCartValue(dto.getMinimumCartValue());
        coupon.setExpiryDate(dto.getExpiryDate());
        coupon.setApplicableProductIds(dto.getApplicableProductIds());
        coupon.setBuyProductIds(dto.getBuyProductIds());
        coupon.setGetProductIds(dto.getGetProductIds());
        coupon.setBuyQuantity(dto.getBuyQuantity());
        coupon.setGetQuantity(dto.getGetQuantity());
        coupon.setRepetitionLimit(dto.getRepetitionLimit());
        return coupon;
    }

    // Map from Coupon entity to CouponResponseDTO
    public CouponResponseDTO toResponseDTO(Coupon coupon) {
        CouponResponseDTO responseDTO = new CouponResponseDTO();
        responseDTO.setId(coupon.getId());
        responseDTO.setType(coupon.getType().name()); // Convert Enum to String
        responseDTO.setCode(coupon.getCode());
        responseDTO.setDiscountValue(coupon.getDiscountValue());
        responseDTO.setMinimumCartValue(coupon.getMinimumCartValue());
        responseDTO.setExpiryDate(coupon.getExpiryDate());
        responseDTO.setRepetitionLimit(coupon.getRepetitionLimit());
        return responseDTO;
    }

    private CouponType parseCouponType(String type) {
        try {
            return CouponType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid CouponType: " + type);
        }
    }
}
