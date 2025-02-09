package com.gideon.contact_manager.application.usecase.user;

import com.gideon.contact_manager.application.dto.CreateUserRequest;
import com.gideon.contact_manager.application.dto.UserResponse;
import com.gideon.contact_manager.application.mapper.UserMapper;
import com.gideon.contact_manager.application.service.user.UserService;
import com.gideon.contact_manager.infrastructure.persistence.JpaUserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        var newUser = userMapper.toEntity(request);
        var createdUser = JpaUserRepository.save(newUser);
//        return UserMapper.INSTANCE.toDTO(createdUser);
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
