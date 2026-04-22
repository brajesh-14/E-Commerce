package com.orderService.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderReqDto {

    private Long cartId;
    private BigDecimal totalOrderPrice;
    private List<OrderProductItem> orderItems;
}
