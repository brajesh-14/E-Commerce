package com.cart.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemDto {
    private Long productId;
    private String productName;
    private Long quantity;
    private Double price;
    private Double totalPrice;
    private String imageUrl;
}
