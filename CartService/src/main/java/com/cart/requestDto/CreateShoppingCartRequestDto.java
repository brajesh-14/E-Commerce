package com.cart.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateShoppingCartRequestDto {

    @NotBlank(message = "User ID is required to create a shopping cart")
    private Long userId;
}
