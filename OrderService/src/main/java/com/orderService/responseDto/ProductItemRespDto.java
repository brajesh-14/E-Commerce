package com.orderService.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemRespDto {

    private Long id;
    private Long productId;
    private String productName;
    private Long productQty;
    private BigDecimal pricePerUnit;
    private BigDecimal totalPrice;
}
