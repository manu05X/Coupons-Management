package com.example.DiscountCoupon.exception;


public class CouponNotFoundException extends RuntimeException{
    public CouponNotFoundException(String message){
        super(message);
    }
}
