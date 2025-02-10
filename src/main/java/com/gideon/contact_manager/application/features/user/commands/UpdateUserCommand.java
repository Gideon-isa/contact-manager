package com.gideon.contact_manager.application.features.user.commands;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class UpdateUserCommand {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private Date createdOn;
    private String createdBy;
    private Date modifiedOn;
    private String modifiedBy;

}
