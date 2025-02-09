package com.gideon.contact_manager.application.mapper;

import com.gideon.contact_manager.application.dto.UserResponse;
import com.gideon.contact_manager.application.features.user.commands.create.CreateUserCommand;
import com.gideon.contact_manager.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse toDTO(User user);
    User toEntity(CreateUserCommand createUserCommand);
}
