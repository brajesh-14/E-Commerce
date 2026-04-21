package com.cart.controller;

import com.cart.requestDto.*;
import com.cart.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
@Slf4j
public class CartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping("/check")
    public ResponseEntity<?> checkCart(@RequestBody CheckCartDto checkCartDto){

        return shoppingCartService.checkCart(checkCartDto);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createShoppingCart(@RequestBody CreateShoppingCartRequestDto cartRequestDto){

        return shoppingCartService.createShoppingCart(cartRequestDto);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProductToCart(@RequestBody AddProductToCartRequestDto addProductToCartRequestDto){

        return shoppingCartService.addProductToCart(addProductToCartRequestDto);

    }

    @PostMapping("/decreaseQty")
    public ResponseEntity<?> decreaseProdQty(@RequestBody DecreaseProdQtyDto decreaseProdQtyDto){

        return shoppingCartService.decreaseProdQty(decreaseProdQtyDto);
    }

    @PostMapping("/removeProduct")
    public ResponseEntity<?> removeCartItemFromCart(@RequestBody RemoveProdFromCartDto removeProdFromCartDto){

        return shoppingCartService.removeCartItemFromCart(removeProdFromCartDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCartItems(@RequestParam GetCartItemDto getCartItemDto){

        return shoppingCartService.getAllCartItems(getCartItemDto);
    }

    @PostMapping("/clearCart")
    public ResponseEntity<?> clearCart(@RequestBody CleanCartReqDto cleanCartReqDto){

        return shoppingCartService.clearCart(cleanCartReqDto);
    }
}
