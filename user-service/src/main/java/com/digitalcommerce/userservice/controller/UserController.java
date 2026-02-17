package com.digitalcommerce.userservice.controller;

import com.digitalcommerce.userservice.dto.AuthRequest;
import com.digitalcommerce.userservice.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserInfoService userInfoService;

    @PostMapping
    public ResponseEntity<?> userInfo(@RequestBody AuthRequest authRequest){
        return ResponseEntity.ok(userInfoService.loadUserInfo(authRequest.getUsername()));
    }

}
