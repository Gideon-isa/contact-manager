package com.gideon.contact_manager.application.features.contact.commands;

import lombok.Builder;

import java.util.Date;

@Builder
public class UpdateContactCommand {
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public String contactImage;
    public String physicalAddress;
    public Boolean isFavourite;
    public String group;
    private Date modifiedOn;
    private String modifiedBy;
}
