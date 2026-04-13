package com.user.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDto {

    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private AddressDto address;
    private Long cartId;
}
