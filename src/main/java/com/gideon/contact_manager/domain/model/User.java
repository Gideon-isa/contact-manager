package com.gideon.contact_manager.domain.model;

import com.gideon.contact_manager.domain.Primitives.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "first name cannot be blank")
    @NonNull
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "first name cannot be blank")
    @NonNull
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "Email name cannot be blank")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "user_address", nullable = true)
    private String Address;

    @Column(nullable = true, unique = true)
    private String passwordHash;

    @Column(name = "user_otp", nullable = true)
    private String Otp;

    @Column(nullable = true, unique = true)
    private Boolean IsEmailConfirmed;
}
