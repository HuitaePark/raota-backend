package com.raota.domain.user.controller;

import com.raota.domain.user.controller.response.MyProfileResponse;
import com.raota.domain.user.service.UserService;
import com.raota.global.common.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/me/profile")
    public ResponseEntity<ApiResponse<MyProfileResponse>> getUserProfile(){
        return ResponseEntity.ok(ApiResponse.success(userService.getMyProfile()));
    }
}
