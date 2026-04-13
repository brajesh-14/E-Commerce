package com.user.Service;

import com.user.requestDto.CreateUserRequestDto;
import com.user.requestDto.UpdateAddressRequestDto;
import com.user.requestDto.UserInfoRequestDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> createUser(CreateUserRequestDto request);

    ResponseEntity<?> getUserInfo(UserInfoRequestDto request);

    ResponseEntity<?> updateAddress(UpdateAddressRequestDto request);


}
