package com.productService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Lob
    private String description;
    private String brandName;
    private BigDecimal pricePerUnit;
    private BigDecimal productWholeSalePrice;
    private Long noOfStocks;
//    private String productImageUrl;
}
