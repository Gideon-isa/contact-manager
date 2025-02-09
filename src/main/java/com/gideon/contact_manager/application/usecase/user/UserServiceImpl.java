package com.gideon.contact_manager.application.usecase.user;

import com.gideon.contact_manager.application.features.user.commands.create.CreateUserCommand;
import com.gideon.contact_manager.presentation.apimodels.CreateUserRequest;
import com.gideon.contact_manager.application.dto.UserResponse;
import com.gideon.contact_manager.application.mapper.UserMapper;
import com.gideon.contact_manager.application.service.user.UserService;
import com.gideon.contact_manager.domain.model.User;
import com.gideon.contact_manager.infrastructure.persistence.JpaUserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JpaUserRepository JpaUserRepository;
    private final UserMapper userMapper;

//    @Autowired
//    public UserServiceImpl(final UserMapper userMapper,
//                           final JpaUserRepository userRepository) {
//        this.userMapper = userMapper;
//        this.JpauserRepository = userRepository;
//    }
    @Override
    public UserResponse createUser(CreateUserRequest request) {
        CreateUserCommand cmd = CreateUserCommand
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .createdBy(request.getFirstName())
                .createdOn(Date.from(Instant.now()))
                .build();

        var newUser = userMapper.toEntity(cmd);
        var createdUser = JpaUserRepository.save(newUser);
        return userMapper.toDTO(createdUser);
    }

    @Override
    public UserResponse getUserById(Long id) {
        return null;
    }

    @Override
    public Optional<UserResponse> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<UserResponse> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {

    }
}
