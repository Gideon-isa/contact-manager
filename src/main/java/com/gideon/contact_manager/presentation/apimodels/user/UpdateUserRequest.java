package com.gideon.contact_manager.presentation.apimodels.user;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    @NotNull(message = "First name cannot be null")
    @Size(min = 2, message = "First name must have at least 2 characters")
    private String firstName;

    @NotNull(message = "First name cannot be null")
    @Size(min = 2, message = "First name must have at least 2 characters")
    private String lastName;

    private String address;

    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

}


