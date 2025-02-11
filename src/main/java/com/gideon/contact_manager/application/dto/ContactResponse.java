package com.gideon.contact_manager.application.dto;

import lombok.Data;

@Data
public class ContactResponse {
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public String contactImage;
    public String physicalAddress;
    public String group;
}
