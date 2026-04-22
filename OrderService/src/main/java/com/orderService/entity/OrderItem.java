package com.orderService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private String productName;
    private Long productQuantity;
    private BigDecimal totalPrice;
    private BigDecimal pricePerUnit;
    private String productImageUrl;

    @JsonIgnore
    @ManyToOne
    private ProductOrder order;



}
