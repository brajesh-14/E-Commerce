package com.cart.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductToCartRequestDto {

    private Long cartId;
    private ProductItemDto productItemDto;
}
