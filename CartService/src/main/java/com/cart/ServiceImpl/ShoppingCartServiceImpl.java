package com.cart.ServiceImpl;

import com.cart.entity.CartItem;
import com.cart.entity.ShoppingCart;
import com.cart.repo.CartItemRepo;
import com.cart.repo.ShoppingCartRepo;
import com.cart.requestDto.*;
import com.cart.responseDto.ServiceResponseDto;
import com.cart.responseDto.ShoppingCartResponseDto;
import com.cart.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepo shoppingCartRepo;
    private final CartItemRepo cartItemRepo;

    @Transactional
    @Override
    public ResponseEntity<?> createShoppingCart(CreateShoppingCartRequestDto cartRequestDto) {

        ServiceResponseDto serviceResponseDto = new ServiceResponseDto();

        try{
            ShoppingCart cart = ShoppingCart.builder()
                    .userId(cartRequestDto.getUserId())
                    .build();

            ShoppingCart savedCart = shoppingCartRepo.save(cart);

            ShoppingCartResponseDto shopingCartResponseDto = ShoppingCartResponseDto.builder()
                    .code("200")
                    .message("Cart created successfully")
                    .cartId(savedCart.getId())
                    .build();
            return ResponseEntity.ok(shopingCartResponseDto);
    }     catch (Exception e){
            log.error("Error creating cart: {}", e.getMessage());
            serviceResponseDto.setCode("500");
            serviceResponseDto.setMessage("Failed to create cart");
            return ResponseEntity.status(500).body(serviceResponseDto);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> addProductToCart(AddProductToCartRequestDto addProductDto) {

        ServiceResponseDto response = new ServiceResponseDto();

        try {

            CartItem cartItem = cartItemRepo.findByShoppingCart_IdAndProductId(addProductDto.getCartId(), addProductDto.getProductItemDto().getProductId());

            if (cartItem != null) {
                long updatedQty = cartItem.getQuantity() + 1;

                cartItem.setQuantity(updatedQty);
                cartItem.setTotalPrice(cartItem.getPrice() * updatedQty);
                cartItemRepo.save(cartItem);

                response.setCode("200");
                response.setMessage("Product added successfully to the cart");

                return new ResponseEntity<>(response, HttpStatus.OK);

            } else {
                ShoppingCart cart = shoppingCartRepo.findById(addProductDto.getCartId())
                        .orElseThrow(() -> new RuntimeException("Cart not found with id: " + addProductDto.getCartId()));

                CartItem newCartItem = CartItem.builder()
                        .productId(addProductDto.getProductItemDto().getProductId())
                        .productName(addProductDto.getProductItemDto().getProductName())
                        .quantity(addProductDto.getProductItemDto().getQuantity())
                        .price(addProductDto.getProductItemDto().getPrice())
                        .totalPrice(addProductDto.getProductItemDto().getTotalPrice())
                        .imageUrl(addProductDto.getProductItemDto().getImageUrl())
                        .shoppingCart(cart)
                        .build();

                cartItemRepo.save(newCartItem);
                response.setCode("200");
                response.setMessage("Product Added successfully to the cart");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setCode("500");
            response.setMessage("Internal Server Error !");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> removeCartItemFromCart(RemoveProdFromCartDto removeProdDto) {

        ServiceResponseDto response = new ServiceResponseDto();

        try{
            CartItem item = cartItemRepo.findByShoppingCart_IdAndProductId(removeProdDto.getCartId(), removeProdDto.getProductId());

            if(item == null){
                response.setCode("404");
                response.setMessage("Item not found in the cart");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            cartItemRepo.delete(item);

            response.setCode("200");
            response.setMessage("Item removed from the cart");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error removing cart item {}", e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> checkCart(CheckCartDto checkCartDto) {
        ServiceResponseDto response = new ServiceResponseDto();

        try{
            ShoppingCart shoppingCart = shoppingCartRepo.findByUserId(checkCartDto.getUserId());

            if(!(shoppingCart == null)){
                response.setCode("200");
                response.setMessage("User Cart Exists");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }else{
                response.setCode("404");
                response.setMessage("User Cart not exists");

                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setCode("500");
            response.setMessage("Internal Server Error");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> decreaseProdQty(DecreaseProdQtyDto decreaseProdQtyDto) {

        ServiceResponseDto response = new ServiceResponseDto();

        try{

            CartItem presentCartItem = cartItemRepo.findByShoppingCart_IdAndProductId(decreaseProdQtyDto.getCartId(), decreaseProdQtyDto.getProductId());

            if(presentCartItem == null){
                response.setCode("404");
                response.setMessage("Product not found in the cart");

                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

            }

            if(presentCartItem.getQuantity()==1){
                cartItemRepo.deleteByShoppingCart_IdAndProductId(decreaseProdQtyDto.getCartId(), decreaseProdQtyDto.getProductId());

                response.setCode("200");
                response.setMessage("Product removed from the cart");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            presentCartItem.setQuantity(presentCartItem.getQuantity() - 1);

            double updatedTotal = presentCartItem.getTotalPrice() - presentCartItem.getPrice();

            presentCartItem.setTotalPrice(updatedTotal);
            cartItemRepo.save(presentCartItem);

            response.setCode("200");
            response.setMessage("Product quantity updated successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error decreasing product quantity");

            response.setCode("500");
            response.setMessage("Internal Server Error");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> getAllCartItems(GetCartItemDto getCartItemDto) {

        ServiceResponseDto response = new ServiceResponseDto();

        try{
            ShoppingCart cartItems = shoppingCartRepo.findById(getCartItemDto.getCartId()).orElse(null);

            if(cartItems != null){
                return new ResponseEntity<>(cartItems, HttpStatus.OK);
            }

            response.setCode("404");
            response.setMessage("No Cart Found");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }catch (Exception e){

            log.error("Error fetching cart items {}", e.getMessage());

            response.setCode("500");
            response.setMessage("Internal Server Error");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> clearCart(CleanCartReqDto cleanCartReqDto) {

        ServiceResponseDto response = new ServiceResponseDto();

        try{
//             cartItemRepo.deleteByCartIdProductId(cleanCartReqDto.getCartId(),cleanCartReqDto.getProductIds());
            cartItemRepo.deleteItems(cleanCartReqDto.getCartId(), cleanCartReqDto.getProductIds());
            response.setCode("200");
            response.setCode("Cart cleaned Successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error in cleaning the cart {}", e.getMessage());

            response.setCode("500");
            response.setMessage("Internal Server Error");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
