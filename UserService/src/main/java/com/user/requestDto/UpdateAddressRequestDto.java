package com.user.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAddressRequestDto {

    private Long userId;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
