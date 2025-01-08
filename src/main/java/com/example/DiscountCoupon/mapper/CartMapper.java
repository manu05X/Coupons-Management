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


    public CartDTO toDTO(Cart cart) {
        List<CartItemDTO> cartItemDTOs = cart.getItems().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        CartDTO cartDTO = new CartDTO();
        cartDTO.setItems(cartItemDTOs);
        cartDTO.setTotalAmount(cart.getTotalAmount());

        return cartDTO;
    }

    public CartItemDTO toDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setProductName(cartItem.getProductName());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setPrice(cartItem.getPrice());

        return cartItemDTO;
    }


    public Cart toEntity(CartDTO cartDTO) {
        List<CartItem> cartItems = cartDTO.getItems().stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

        Cart cart = new Cart();
        cart.setItems(cartItems);
        cart.setTotalAmount(cartDTO.getTotalAmount());

        return cart;
    }


    public CartItem toEntity(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setProductName(cartItemDTO.getProductName());
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setPrice(cartItemDTO.getPrice());

        return cartItem;
    }
}
