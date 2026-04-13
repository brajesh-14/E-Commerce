package com.user.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponseDto {

    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private AddressDto address;
    private Long cartId;
}
