package com.gideon.contact_manager.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
}
