package com.example.DiscountCoupon.exception;

public class InvalidCouponException extends RuntimeException{
    public InvalidCouponException(String message){
        super(message);
    }
}
