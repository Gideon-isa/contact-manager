package com.gideon.contact_manager.application.service.user;

import com.gideon.contact_manager.presentation.apimodels.CreateUserRequest;
import com.gideon.contact_manager.application.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
    UserResponse getUserById(Long id);
    Optional<UserResponse> findById(Long id);
    List<UserResponse> findAll();
    void deleteById(Long id);

}
