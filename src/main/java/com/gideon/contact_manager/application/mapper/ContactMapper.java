package com.gideon.contact_manager.application.mapper;

import com.gideon.contact_manager.application.dto.UserResponse;
import com.gideon.contact_manager.domain.model.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContactMapper {
//    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    UserResponse toDTO(Contact contact);
    Contact toEntity(UserResponse contactDTO);
}
