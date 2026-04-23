package com.orderService.responseDto;

import com.orderService.requestDto.OrderProductItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRespDto {

    private Long orderId;
    private BigDecimal totalOrderPrice;
    private String orderStatus;
    List<ProductItemRespDto> orderItems;
    private Date createdAt;
    private Date updatedAt;

}
