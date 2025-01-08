package com.example.DiscountCoupon.mapper;

import com.example.DiscountCoupon.dto.CartDTO;
import com.example.DiscountCoupon.dto.CartItemDTO;
import com.example.DiscountCoupon.model.Cart;
import com.example.DiscountCoupon.model.CartItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    // Map from Cart model to CartDTO
    public CartDTO toDTO(Cart cart) {
        List<CartItemDTO> cartItemDTOs = cart.getItems().stream()
                .map(this::toDTO)  // Convert each CartItem to CartItemDTO
                .collect(Collectors.toList());

        CartDTO cartDTO = new CartDTO();
        cartDTO.setItems(cartItemDTOs);
        cartDTO.setTotalAmount(cart.getTotalAmount());

        return cartDTO;
    }

    // Map from CartItem model to CartItemDTO
    public CartItemDTO toDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setProductName(cartItem.getProductName());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setPrice(cartItem.getPrice());

        return cartItemDTO;
    }

    // Optionally, map CartDTO back to Cart model if needed
    public Cart toEntity(CartDTO cartDTO) {
        List<CartItem> cartItems = cartDTO.getItems().stream()
                .map(this::toEntity)  // Convert each CartItemDTO back to CartItem
                .collect(Collectors.toList());

        Cart cart = new Cart();
        cart.setItems(cartItems);
        cart.setTotalAmount(cartDTO.getTotalAmount());

        return cart;
    }

    // Optionally, map CartItemDTO back to CartItem model if needed
    public CartItem toEntity(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setProductName(cartItemDTO.getProductName());
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setPrice(cartItemDTO.getPrice());

        return cartItem;
    }
}
