package com.gideon.contact_manager.presentation.apimodels.user;

import com.gideon.contact_manager.presentation.validations.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordMatches
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

    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    @NotNull(message = "Password is required")
    @Size(min = 6, message = "minimum of 6 character is required")
    @Pattern(
            regexp = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$",
            message = "Password must contain at least one special character"
    )
    private String password;

    @NotNull(message = "Password is required")
    @Size(min = 6, message = "minimum of 6 character is required")
    @Pattern(
            regexp = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$",
            message = "Password must contain at least one special character"
    )
    private String confirmPassword;
}
