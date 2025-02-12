package com.gideon.contact_manager.application.features.contact.commands;

import com.gideon.contact_manager.domain.model.ContactGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@Builder
public class CreateContactCommand {
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public String contactImage;
    public String physicalAddress;
    public Boolean isFavourite;
    public ContactGroup group;
    private Date createdOn;
    private String createdBy;
    private Date modifiedOn;
    private String modifiedBy;
}
