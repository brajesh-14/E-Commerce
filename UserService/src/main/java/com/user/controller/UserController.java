package com.user.controller;

import com.user.Service.UserService;
import com.user.requestDto.CreateUserRequestDto;
import com.user.requestDto.UpdateAddressRequestDto;
import com.user.requestDto.UserInfoRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequestDto request) {
        ResponseEntity<?> user = userService.createUser(request);

        return user;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable UserInfoRequestDto request) {
        userService.getUserInfo(request);

        return ResponseEntity.ok("User info retrieved successfully");
    }

    @PutMapping("/update-address")
    public ResponseEntity<?> updateAddress(@RequestBody UpdateAddressRequestDto request) {
        userService.updateAddress(request);

        return ResponseEntity.ok("Address updated successfully");
    }
}