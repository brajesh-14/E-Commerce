package com.orderService.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductItemDto {

    private Long productId;
    private String productName;
    private Long productQty;
    private BigDecimal totalPrice;
    private BigDecimal pricePerUnit;

}
