package com.digitalcommerce.userservice.controller;

import com.digitalcommerce.userservice.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserInfoService userInfoService;

    @GetMapping
    public ResponseEntity<?> getAllUsersInfo(){
        return ResponseEntity.ok(userInfoService.loadAllUserInfo());
    }
}
