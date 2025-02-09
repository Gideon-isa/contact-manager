package com.gideon.contact_manager.presentation.apimodels;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    @NotNull(message = "First name cannot be null")
    @Size(min = 2, message = "First name must have at least 2 characters")
    private String firstName;

    @NotNull(message = "First name cannot be null")
    @Size(min = 2, message = "First name must have at least 2 characters")
    private String lastName;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
}
