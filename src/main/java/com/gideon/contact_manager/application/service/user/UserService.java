package com.gideon.contact_manager.application.service.user;

import com.gideon.contact_manager.presentation.apimodels.user.CreateUserRequest;
import com.gideon.contact_manager.application.dto.UserResponse;
import com.gideon.contact_manager.presentation.apimodels.user.LoginRequest;
import com.gideon.contact_manager.presentation.apimodels.user.UpdateUserRequest;
import com.gideon.contact_manager.shared.BaseResponse;

import java.util.List;

public interface UserService {
    BaseResponse<UserResponse> createUser(CreateUserRequest request);
    BaseResponse<UserResponse> login(LoginRequest request);
    BaseResponse<UserResponse> getUserById(Long id);
    BaseResponse<UserResponse> updateUser(Long id, UpdateUserRequest updateUserRequest);
    BaseResponse<List<UserResponse>> GetAllUsers();
    BaseResponse<UserResponse> deleteUser(Long id);

}
