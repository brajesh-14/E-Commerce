package com.user.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private Long addressId;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
