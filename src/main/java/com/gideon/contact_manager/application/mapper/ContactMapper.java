package com.gideon.contact_manager.application.mapper;

import com.gideon.contact_manager.application.dto.ContactResponse;
import com.gideon.contact_manager.application.features.contact.commands.CreateContactCommand;
import com.gideon.contact_manager.domain.model.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {
//    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    ContactResponse toDTO(Contact contact);
    Contact toEntity(CreateContactCommand contactCommand);
}
