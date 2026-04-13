package com.user.ServiceImpl;

import com.user.Service.UserService;
import com.user.entity.Address;
import com.user.entity.User;
import com.user.repo.UserRepo;
import com.user.requestDto.UpdateAddressRequestDto;
import com.user.responseDto.AddressDto;
import com.user.requestDto.CreateUserRequestDto;
import com.user.requestDto.UserInfoRequestDto;
import com.user.responseDto.ServiceResponseDto;
import com.user.responseDto.UserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public ResponseEntity<?> createUser(CreateUserRequestDto request) {

        ServiceResponseDto response = new ServiceResponseDto();

        try {

            Address address = null;

            if (request.getAddress() != null) {
                address = Address.builder()
                        .street(request.getAddress().getStreet())
                        .city(request.getAddress().getCity())
                        .state(request.getAddress().getState())
                        .zipCode(request.getAddress().getZipCode())
                        .country(request.getAddress().getCountry())
                        .build();
            }

            User user = User.builder()
                    .userId(request.getUserId())
                    .userName(request.getUserName())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .cartId(request.getCartId())
                    .address(address)
                    .build();

            userRepo.save(user);
            response.setCode("200");
            response.setMessage("User created successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage());
            response.setCode("500");
            response.setMessage("Error creating user: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }

    }

    @Override
    public ResponseEntity<?> getUserInfo(UserInfoRequestDto request) {

        try {
            User user = (User) userRepo.findByUserId(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

            AddressDto address = null;

            if (user.getAddress() != null) {
                address = AddressDto.builder()
                        .street(user.getAddress().getStreet())
                        .city(user.getAddress().getCity())
                        .state(user.getAddress().getState())
                        .zipCode(user.getAddress().getZipCode())
                        .country(user.getAddress().getCountry())
                        .build();
            }

            UserInfoResponseDto userInfo = UserInfoResponseDto.builder()
                    .userId(user.getUserId())
                    .userName(user.getUserName())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .cartId(user.getCartId())
                    .address(address)
                    .build();

            return ResponseEntity.ok(userInfo);

        } catch (RuntimeException e) {
            log.error("Error fetching user info: {}", e.getMessage());

            ServiceResponseDto response = new ServiceResponseDto();
            response.setCode("404");
            response.setMessage("User info retrieved successfully");

            return ResponseEntity.status(404).body(response);
        } catch (Exception e) {
            log.error("Error fetching user info: {}", e.getMessage());

            ServiceResponseDto response = new ServiceResponseDto();
            response.setCode("500");
            response.setMessage("Error fetching user info: " + e.getMessage());
            return ResponseEntity.status(500).body(response);

        }

    }

    @Override
    public ResponseEntity<?> updateAddress(UpdateAddressRequestDto request) {

        try{
            User user = (User) userRepo.findByUserId(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

            Address address;

            // ✅ If address exists → update normally
            if (user.getAddress() != null) {

                address = user.getAddress();
                address.setStreet(request.getStreet());
                address.setCity(request.getCity());
                address.setState(request.getState());
                address.setZipCode(request.getZipCode());
                address.setCountry(request.getCountry());

            } else {
                // ✅ If new address → use Builder
                address = Address.builder()
                        .street(request.getStreet())
                        .city(request.getCity())
                        .state(request.getState())
                        .zipCode(request.getZipCode())
                        .country(request.getCountry())
                        .build();
            }

            user.setAddress(address);
            userRepo.save(user);

            ServiceResponseDto serviceResponse = new ServiceResponseDto();
            serviceResponse.setCode("200");
            serviceResponse.setMessage("Address updated successfully");

            return ResponseEntity.ok(serviceResponse);

        }catch (RuntimeException e) {
            log.error("Error updating address: {}", e.getMessage());

            ServiceResponseDto response = new ServiceResponseDto();
            response.setCode("404");
            response.setMessage(e.getMessage());

            return ResponseEntity.status(404).body(response);
        } catch (Exception e) {
            log.error("Error updating address: {}", e.getMessage());

            ServiceResponseDto response = new ServiceResponseDto();
            response.setCode("500");
            response.setMessage("Internal Server Error " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
