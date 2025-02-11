package com.gideon.contact_manager.presentation.controllers;

import com.gideon.contact_manager.presentation.apimodels.CreateUserRequest;
import com.gideon.contact_manager.application.dto.UserResponse;
import com.gideon.contact_manager.application.service.user.UserService;
import com.gideon.contact_manager.presentation.apimodels.LoginRequest;
import com.gideon.contact_manager.presentation.apimodels.UpdateUserRequest;
import com.gideon.contact_manager.shared.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<BaseResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("creating an new user with email: {}", request.getEmail());
        var response = userService.createUser(request);
        var myStatusCode = response.getStatus();
        System.out.println(response.getStatus());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<UserResponse>> login(@Valid @RequestBody LoginRequest request) {
        log.info("logging user with email in: {}", request.getEmail());
        var response = userService.login(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> getUserById(@PathVariable Long id) {
        log.info("Fetching user with ID: {}", id);
        BaseResponse<UserResponse> response = userService.getUserById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> updateUser(
            @PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        log.info("logging user with id: {}", id);
        var response = userService.updateUser(id, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> deleteUserById(@PathVariable Long id) {
        log.info("deleting user with ID: {}", id);
        BaseResponse<UserResponse> response = userService.deleteUser(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
