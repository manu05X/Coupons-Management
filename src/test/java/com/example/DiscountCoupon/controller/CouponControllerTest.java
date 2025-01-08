package com.example.DiscountCoupon.controller;

import com.example.DiscountCoupon.dto.*;
import com.example.DiscountCoupon.mapper.CartMapper;
import com.example.DiscountCoupon.mapper.CouponMapper;
import com.example.DiscountCoupon.model.Cart;
import com.example.DiscountCoupon.model.Coupon;
import com.example.DiscountCoupon.service.CouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CouponControllerTest {

    @InjectMocks
    private CouponController couponController;

    @Mock
    private CouponService couponService;

    @Mock
    private CouponMapper couponMapper;

    @Mock
    private CartMapper cartMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCoupon() {
        // Mock input
        CouponRequestDTO requestDTO = new CouponRequestDTO();
        Coupon coupon = new Coupon();
        Coupon savedCoupon = new Coupon();
        CouponResponseDTO responseDTO = new CouponResponseDTO();

        when(couponMapper.toEntity(requestDTO)).thenReturn(coupon);
        when(couponService.createCoupon(coupon)).thenReturn(savedCoupon);
        when(couponMapper.toResponseDTO(savedCoupon)).thenReturn(responseDTO);

        // Call the method
        ResponseEntity<CouponResponseDTO> response = couponController.createCoupon(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals(responseDTO, response.getBody());
        verify(couponMapper).toEntity(requestDTO);
        verify(couponService).createCoupon(coupon);
        verify(couponMapper).toResponseDTO(savedCoupon);
    }

    @Test
    public void testGetAllCoupons() {
        // Mock response
        List<CouponResponseDTO> mockCoupons = Arrays.asList(new CouponResponseDTO(), new CouponResponseDTO());
        when(couponService.getAllCoupons()).thenReturn(mockCoupons);

        // Call the method
        ResponseEntity<List<CouponResponseDTO>> response = couponController.getAllCoupons();

        // Assert
        assertNotNull(response);
        assertEquals(mockCoupons, response.getBody());
        verify(couponService).getAllCoupons();
    }

    @Test
    public void testGetCouponById() {
        // Mock input and response
        Long couponId = 1L;
        CouponResponseDTO mockResponse = new CouponResponseDTO();
        when(couponService.getCouponById(couponId)).thenReturn(mockResponse);

        // Call the method
        ResponseEntity<CouponResponseDTO> response = couponController.getCouponById(couponId);

        // Assert
        assertNotNull(response);
        assertEquals(mockResponse, response.getBody());
        verify(couponService).getCouponById(couponId);
    }

    @Test
    public void testUpdateCoupon() {
        // Mock input and response
        Long couponId = 1L;
        CouponRequestDTO requestDTO = new CouponRequestDTO();
        Coupon coupon = new Coupon();
        Coupon updatedCoupon = new Coupon();
        CouponResponseDTO responseDTO = new CouponResponseDTO();

        when(couponMapper.toEntity(requestDTO)).thenReturn(coupon);
        when(couponService.updateCoupon(couponId, coupon)).thenReturn(updatedCoupon);
        when(couponMapper.toResponseDTO(updatedCoupon)).thenReturn(responseDTO);

        // Call the method
        ResponseEntity<CouponResponseDTO> response = couponController.updateCoupon(couponId, requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals(responseDTO, response.getBody());
        verify(couponMapper).toEntity(requestDTO);
        verify(couponService).updateCoupon(couponId, coupon);
        verify(couponMapper).toResponseDTO(updatedCoupon);
    }

    @Test
    public void testDeleteCoupon() {
        // Mock input
        Long couponId = 1L;

        // Call the method
        ResponseEntity<Void> response = couponController.deleteCoupon(couponId);

        // Assert
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(couponService).deleteCoupon(couponId);
    }

    @Test
    public void testGetApplicableCoupons() {
        // Mock input and response
        CartDTO cartDTO = new CartDTO();
        Cart cart = new Cart();
        List<ApplicableCouponResponseDTO> mockApplicableCoupons = Arrays.asList(new ApplicableCouponResponseDTO());

        when(cartMapper.toEntity(cartDTO)).thenReturn(cart);
        when(couponService.getApplicableCoupons(cart)).thenReturn(mockApplicableCoupons);

        // Call the method
        ResponseEntity<List<ApplicableCouponResponseDTO>> response = couponController.getApplicableCoupons(cartDTO);

        // Assert
        assertNotNull(response);
        assertEquals(mockApplicableCoupons, response.getBody());
        verify(cartMapper).toEntity(cartDTO);
        verify(couponService).getApplicableCoupons(cart);
    }

    @Test
    public void testApplyCoupon() {
        // Mock input and response
        ApplyCouponRequestDTO requestDTO = new ApplyCouponRequestDTO();
        CartDTO cartDTO = new CartDTO();
        Cart cart = new Cart();
        Cart updatedCart = new Cart();
        CartDTO updatedCartDTO = new CartDTO();

        requestDTO.setCart(cartDTO);
        requestDTO.setCouponCode("COUPON123");

        when(cartMapper.toEntity(cartDTO)).thenReturn(cart);
        when(couponService.applyCoupon("COUPON123", cart)).thenReturn(updatedCart);
        when(cartMapper.toDTO(updatedCart)).thenReturn(updatedCartDTO);

        // Call the method
        ResponseEntity<CartDTO> response = couponController.applyCoupon(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals(updatedCartDTO, response.getBody());
        verify(cartMapper).toEntity(cartDTO);
        verify(couponService).applyCoupon("COUPON123", cart);
        verify(cartMapper).toDTO(updatedCart);
    }
}
