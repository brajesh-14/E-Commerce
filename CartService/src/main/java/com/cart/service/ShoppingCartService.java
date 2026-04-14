package com.cart.service;

import com.cart.requestDto.*;
import org.springframework.http.ResponseEntity;

public interface ShoppingCartService {

    ResponseEntity<?> createShoppingCart(CreateShoppingCartRequestDto cartRequestDto);

    ResponseEntity<?> addProductToCart(AddProductToCartRequestDto addProductDto);

    ResponseEntity<?> removeCartItemFromCart(RemoveProdFromCartDto removeProdDto);

    ResponseEntity<?> checkCart(CheckCartDto checkCartDto);

    ResponseEntity<?> decreaseProdQty(DecreaseProdQtyDto decreaseProdQtyDto);

    ResponseEntity<?> getAllCartItems(GetCartItemDto getCartItemDto);

    ResponseEntity<?> clearCart(CleanCartReqDto cleanCartReqDto);
}
