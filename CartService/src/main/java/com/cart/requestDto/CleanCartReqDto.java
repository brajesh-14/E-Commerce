package com.cart.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CleanCartReqDto {

    private Long cartId;
    private List<Long> productIds;
}
