package com.cart.ServiceImpl;

import com.cart.requestDto.*;
import com.cart.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {


    @Override
    public ResponseEntity<?> createShoppingCart(CreateShoppingCartRequestDto cartRequestDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> addProductToCart(AddProductToCartRequestDto addProductDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> removeCartItemFromCart(RemoveProdFromCartDto removeProdDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> checkCart(CheckCartDto checkCartDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> decreaseProdQty(DecreaseProdQtyDto decreaseProdQtyDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllCartItems(GetCartItemDto getCartItemDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> clearCart(CleanCartReqDto cleanCartReqDto) {
        return null;
    }
}
