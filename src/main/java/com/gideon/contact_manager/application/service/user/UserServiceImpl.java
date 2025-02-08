package com.gideon.contact_manager.application.service.user;

import com.gideon.contact_manager.application.dto.CreateUserRequest;
import com.gideon.contact_manager.application.dto.UserResponse;
import com.gideon.contact_manager.application.mapper.UserMapper;
import com.gideon.contact_manager.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(final UserMapper userMapper,
                           final UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }
    @Override
    public UserResponse createUser(CreateUserRequest request) {
        var newUser = userMapper.toEntity(request);
        var createdUser = userRepository.save(newUser);
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
