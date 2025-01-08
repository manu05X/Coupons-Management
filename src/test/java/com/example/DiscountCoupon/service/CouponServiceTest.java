package com.example.DiscountCoupon.service;

import com.example.DiscountCoupon.dto.ApplicableCouponResponseDTO;
import com.example.DiscountCoupon.dto.CouponResponseDTO;
import com.example.DiscountCoupon.exception.CouponNotFoundException;
import com.example.DiscountCoupon.exception.InvalidCouponException;
import com.example.DiscountCoupon.model.Cart;
import com.example.DiscountCoupon.model.CartItem;
import com.example.DiscountCoupon.model.Coupon;
import com.example.DiscountCoupon.model.CouponType;
import com.example.DiscountCoupon.repository.CouponRepository;
import com.example.DiscountCoupon.strategy.CouponStrategy;
import com.example.DiscountCoupon.mapper.CouponMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private Map<CouponType, CouponStrategy> strategyMap;

    @Mock
    private CouponMapper couponMapper;

    @InjectMocks
    private CouponService couponService;

    private Coupon coupon;
    private Cart cart;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up mock Coupon
        coupon = new Coupon();
        coupon.setId(1L);
        coupon.setType(CouponType.BUY_X_GET_Y);
        coupon.setCode("DISCOUNT10");
        coupon.setDiscountValue(10.0);
        coupon.setExpiryDate(LocalDateTime.now().plusDays(10));

        // Set up mock Cart
        cart = new Cart();
        cart.setTotalAmount(100.0);
    }

    @Test
    public void testCreateCoupon() {
        when(couponRepository.save(any(Coupon.class))).thenReturn(coupon);

        Coupon createdCoupon = couponService.createCoupon(coupon);

        assertNotNull(createdCoupon);
        assertEquals(coupon.getCode(), createdCoupon.getCode());
        verify(couponRepository, times(1)).save(any(Coupon.class));
    }

    @Test
    public void testGetAllCoupons() {
        CouponResponseDTO responseDTO = new CouponResponseDTO();
        responseDTO.setCode("DISCOUNT10");

        when(couponRepository.findAll()).thenReturn(Arrays.asList(coupon));
        when(couponMapper.toResponseDTO(any(Coupon.class))).thenReturn(responseDTO);

        List<CouponResponseDTO> coupons = couponService.getAllCoupons();

        assertNotNull(coupons);
        assertEquals(1, coupons.size());
        assertEquals("DISCOUNT10", coupons.get(0).getCode());
    }

    @Test
    public void testGetCouponById() {
        when(couponRepository.findById(1L)).thenReturn(Optional.of(coupon));
        when(couponMapper.toResponseDTO(any(Coupon.class))).thenReturn(new CouponResponseDTO());

        CouponResponseDTO couponDTO = couponService.getCouponById(1L);

        assertNotNull(couponDTO);
        verify(couponRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCouponByIdNotFound() {
        when(couponRepository.findById(1L)).thenReturn(Optional.empty());

        CouponNotFoundException thrown = assertThrows(CouponNotFoundException.class, () -> {
            couponService.getCouponById(1L);
        });

        assertEquals("Coupon not found with id: 1", thrown.getMessage());
    }

    @Test
    public void testUpdateCoupon() {
        coupon.setDiscountValue(20.0);
        when(couponRepository.existsById(1L)).thenReturn(true);
        when(couponRepository.save(any(Coupon.class))).thenReturn(coupon);

        Coupon updatedCoupon = couponService.updateCoupon(1L, coupon);

        assertNotNull(updatedCoupon);
        assertEquals(20.0, updatedCoupon.getDiscountValue());
    }

    @Test
    public void testDeleteCoupon() {
        when(couponRepository.existsById(1L)).thenReturn(true);
        doNothing().when(couponRepository).deleteById(1L);

        couponService.deleteCoupon(1L);

        verify(couponRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteCouponNotFound() {
        when(couponRepository.existsById(1L)).thenReturn(false);

        CouponNotFoundException thrown = assertThrows(CouponNotFoundException.class, () -> {
            couponService.deleteCoupon(1L);
        });

        assertEquals("Coupon not found with id: 1", thrown.getMessage());
    }

    @Test
    public void testGetApplicableCoupons() {
        // Create CartItems
        CartItem item1 = new CartItem();
        item1.setProductId(1L);
        item1.setPrice(100.0);
        item1.setQuantity(2);

        CartItem item2 = new CartItem();
        item2.setProductId(2L);
        item2.setPrice(50.0);
        item2.setQuantity(3);

        Cart cart = new Cart();
        cart.setItems(Arrays.asList(item1, item2));

        Coupon coupon = new Coupon();
        coupon.setCode("COUPON123");
        coupon.setType(CouponType.BUY_X_GET_Y);
        coupon.setBuyQuantity(2);
        coupon.setGetProductIds(Arrays.asList(2L));

        CouponStrategy strategy = mock(CouponStrategy.class);
        when(strategy.isApplicable(any(Coupon.class), eq(cart))).thenReturn(true);
        when(strategy.calculateDiscount(any(Coupon.class), eq(cart))).thenReturn(10.0);

        when(strategyMap.get(CouponType.BUY_X_GET_Y)).thenReturn(strategy);

        when(couponService.getAllValidCoupons()).thenReturn(Arrays.asList(coupon));

        List<ApplicableCouponResponseDTO> applicableCoupons = couponService.getApplicableCoupons(cart);

        // Debug logs
        System.out.println("Strategy Map: " + strategyMap.get(CouponType.BUY_X_GET_Y));
        System.out.println("Is Applicable: " + strategy.isApplicable(coupon, cart));
        System.out.println("Calculated Discount: " + strategy.calculateDiscount(coupon, cart));
        applicableCoupons.forEach(System.out::println);

        // Assertions
        assertNotNull(applicableCoupons);
        assertEquals(1, applicableCoupons.size());
        assertEquals("COUPON123", applicableCoupons.get(0).getCouponCode());
        assertEquals(10.0, applicableCoupons.get(0).getDiscount());
    }






    @Test
    public void testApplyCoupon() {
        when(couponRepository.findByCode("DISCOUNT10")).thenReturn(Optional.of(coupon));
        CouponStrategy strategy = mock(CouponStrategy.class);
        when(strategy.isApplicable(any(Coupon.class), any(Cart.class))).thenReturn(true);
        when(strategy.calculateDiscount(any(Coupon.class), any(Cart.class))).thenReturn(10.0);
        when(strategyMap.get(CouponType.BUY_X_GET_Y)).thenReturn(strategy);

        Cart updatedCart = couponService.applyCoupon("DISCOUNT10", cart);

        assertNotNull(updatedCart);
        assertEquals(90.0, updatedCart.getTotalAmount());
    }

    @Test
    public void testApplyCouponNotFound() {
        when(couponRepository.findByCode("INVALID_CODE")).thenReturn(Optional.empty());

        CouponNotFoundException thrown = assertThrows(CouponNotFoundException.class, () -> {
            couponService.applyCoupon("INVALID_CODE", cart);
        });

        assertEquals("Coupon not found with code: INVALID_CODE", thrown.getMessage());
    }

    @Test
    public void testApplyCouponExpired() {
        coupon.setExpiryDate(LocalDateTime.now().minusDays(1));
        when(couponRepository.findByCode("DISCOUNT10")).thenReturn(Optional.of(coupon));

        InvalidCouponException thrown = assertThrows(InvalidCouponException.class, () -> {
            couponService.applyCoupon("DISCOUNT10", cart);
        });

        assertEquals("Coupon has expired", thrown.getMessage());
    }
}
