package com.gideon.contact_manager.application.dto;

import com.gideon.contact_manager.domain.model.ContactGroup;
import lombok.Data;

@Data
public class UserResponse {
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
//    private String phoneNumber;
//    private String imageUrl;
//    private String address;
//    private ContactGroup group;
}
