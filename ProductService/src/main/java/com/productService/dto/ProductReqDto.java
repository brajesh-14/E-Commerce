package com.productService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReqDto {

    private String name;
    private String description;
    private String brandName;
    private BigDecimal pricePerUnit;
    private BigDecimal productWholeSalePrice;
    private Long noOfStocks;
}
