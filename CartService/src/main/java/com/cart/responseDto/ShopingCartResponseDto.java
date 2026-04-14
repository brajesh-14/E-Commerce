package com.cart.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopingCartResponseDto {

    private String code;
    private String message;
    private Long cartId;
}
