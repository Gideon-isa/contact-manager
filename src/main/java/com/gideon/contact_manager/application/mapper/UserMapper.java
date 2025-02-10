package com.gideon.contact_manager.application.mapper;

import com.gideon.contact_manager.application.dto.UserResponse;
import com.gideon.contact_manager.application.features.user.commands.CreateUserCommand;
import com.gideon.contact_manager.application.features.user.commands.UpdateUserCommand;
import com.gideon.contact_manager.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse toDTO(User user);
    User createUserToEntity(CreateUserCommand createUserCommand);
    User updateUserToEntity(UpdateUserCommand updateUserCommand);

}
