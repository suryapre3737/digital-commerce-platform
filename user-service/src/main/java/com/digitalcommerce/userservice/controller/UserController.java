package com.digitalcommerce.userservice.controller;

import com.digitalcommerce.userservice.dto.ApiResponse;
import com.digitalcommerce.userservice.dto.AuthRequest;
import com.digitalcommerce.userservice.dto.UserAddressInfoRq;
import com.digitalcommerce.userservice.entity.UserInfo;
import com.digitalcommerce.userservice.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserInfoService userInfoService;

    @PostMapping("/info")
    public ResponseEntity<?> userInfo(@RequestBody AuthRequest authRequest){
        return ResponseEntity.ok(userInfoService.loadUserInfo(authRequest.getUsername()));
    }

    @PostMapping("/addAddress")
    public ResponseEntity<ApiResponse<UserInfo>> addUserAddressInfo(@RequestBody UserAddressInfoRq userAddressInfoRq){
        ApiResponse<UserInfo> response = userInfoService.addUserAddressInfo(userAddressInfoRq);
        if(response.getStatusCode() == 201) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else if(response.getStatusCode() == 400) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/updateAddress")
    public ResponseEntity<ApiResponse<UserInfo>> updateUserAddressInfo(@RequestBody UserAddressInfoRq userAddressInfoRq){
        ApiResponse<UserInfo> response = userInfoService.updateUserAddressInfo(userAddressInfoRq);
        if(response.getStatusCode() == 204) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } else if(response.getStatusCode() == 400) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/removeAddress")
    public ResponseEntity<ApiResponse<UserInfo>> removeUserAddressInfo(@RequestParam String username, @RequestParam Long addressId){
        ApiResponse<UserInfo> response = userInfoService.removeUserAddressInfo(username, addressId);
        if(response.getStatusCode() == 201) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else if(response.getStatusCode() == 400) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
