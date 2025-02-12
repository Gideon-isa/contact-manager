package com.gideon.contact_manager.application.usecase.user;

import com.gideon.contact_manager.application.features.user.commands.CreateUserCommand;
import com.gideon.contact_manager.application.features.user.commands.LoginUserCommand;
import com.gideon.contact_manager.application.features.user.commands.UpdateUserCommand;
import com.gideon.contact_manager.domain.model.Role;
import com.gideon.contact_manager.domain.model.User;
import com.gideon.contact_manager.presentation.apimodels.user.CreateUserRequest;
import com.gideon.contact_manager.application.dto.UserResponse;
import com.gideon.contact_manager.application.mapper.UserMapper;
import com.gideon.contact_manager.application.service.user.UserService;
import com.gideon.contact_manager.infrastructure.persistence.JpaUserRepository;
import com.gideon.contact_manager.presentation.apimodels.user.LoginRequest;
import com.gideon.contact_manager.presentation.apimodels.user.UpdateUserRequest;
import com.gideon.contact_manager.shared.BaseResponse;
import com.gideon.contact_manager.shared.Error;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;
    private final PasswordServiceImpl passwordService;

    @Override
    public BaseResponse<UserResponse> createUser(CreateUserRequest request) {
        // compute
        String passwordHash = passwordService.ComputePasswordHash(request.getPassword());
        CreateUserCommand cmd = CreateUserCommand
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .passwordHash(passwordHash)
                .phoneNumber(request.getPhoneNumber())
                .createdBy(request.getFirstName())
                .createdOn(Date.from(Instant.now()))
                .build();

        var newUser = userMapper.createUserToEntity(cmd);
        //Setting the user role
        newUser.setRole(Role.USER);

        // TODO
        // Need to send otp to user's mail for confirmation

        var createdUser = jpaUserRepository.save(newUser);
        UserResponse userDto =  userMapper.toDTO(createdUser);
        return BaseResponse
                .success(userDto, HttpStatus.CREATED.value(), "user successfully created");
    }
    @Override
    public BaseResponse<UserResponse> login(LoginRequest request) {
        LoginUserCommand cmd = LoginUserCommand
                .builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        String loginPasswordHash = passwordService.ComputePasswordHash(cmd.getPassword());
        Optional<User> user =  jpaUserRepository.findByEmail(cmd.getEmail());

        if(user.isPresent()) {
            // compare login hash password with existing user
            var isValid = user.get().getPassword().equals(loginPasswordHash);
            if(isValid) {
                return BaseResponse.success(
                        user.map(userMapper::toDTO).orElseThrow(),
                        HttpStatus.OK.value(), "User login successfully");
            }
            return BaseResponse.failure(new UserResponse(),
                    new Error("404", "password does not match"),
                    HttpStatus.NOT_FOUND.value(), "");
        };

        return BaseResponse.failure(new UserResponse(),
                new Error("404", "user does not exist"),
                HttpStatus.NOT_FOUND.value(), "");
    }
    @Override
    public BaseResponse<UserResponse> getUserById(Long id) {
        Optional<User> user = jpaUserRepository.findById(id);
        if(user.isPresent()) {
            UserResponse userDto = user.map(userMapper::toDTO).orElse(null);
            return BaseResponse.success(userDto, HttpStatus.OK.value(), "User found");
        }
        return BaseResponse.failure(new UserResponse(),
                new Error("404", "No user of such exist in the application"),
                HttpStatus.NO_CONTENT.value(), "User does not exist");
    }

    @Override
    public BaseResponse<UserResponse> updateUser(Long id, UpdateUserRequest request) {
        Optional<User> user = jpaUserRepository.findById(id);
        if(user.isPresent()) {
            user.get().setFirstName(request.getFirstName());
            user.get().setLastName(request.getLastName());
            user.get().setPhoneNumber(request.getPhoneNumber());
            user.get().setAddress(request.getAddress());

            jpaUserRepository.save(user.get());

            UserResponse userDto = userMapper.toDTO(user.get());
            return BaseResponse.success(userDto, HttpStatus.OK.value(), "User updated successfully");
        }
        return BaseResponse.failure(new UserResponse(),
                new Error("404", "No user of such exist in the application"),
                HttpStatus.NO_CONTENT.value(), "User does not exist");

    }

    @Override
    public BaseResponse<List<UserResponse>> GetAllUsers() {
        List<UserResponse> users = jpaUserRepository
                .findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
        int totalUsers = users.size();
        return BaseResponse.success(users, HttpStatus.OK.value(), String.format("returned %d user", totalUsers));
    }

    @Override
    public BaseResponse<UserResponse> deleteUser(Long id) {
        var result = getUserById(id);
        if(result.getData() != null) {
            jpaUserRepository.deleteById(id);
            return BaseResponse.success(result.getData(), HttpStatus.OK.value(),
                    String.format("user with id %d has been deleted", result.getData().id));
        };
        return BaseResponse.failure(new UserResponse(),
                new Error("404", "user does not exist in the system"),
                HttpStatus.NO_CONTENT.value(), "User does not exist");
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return jpaUserRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
