package com.gideon.contact_manager.application.features.contact.commands;

import java.util.Date;

public class UpdateContactCommand {
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public String contactImage;
    public String physicalAddress;
    public String group;
    private Date createdOn;
    private String createdBy;
}
