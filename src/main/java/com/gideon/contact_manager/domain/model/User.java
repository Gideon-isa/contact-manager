package com.gideon.contact_manager.domain.model;

import com.gideon.contact_manager.domain.Primitives.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Date;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class User extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "first name cannot be blank")
    @NonNull
    @Column(nullable = false, unique = true)
    private String firstName;

    @NotBlank(message = "first name cannot be blank")
    @NonNull
    @Column(nullable = false, unique = true)
    private String lastName;

    @NotBlank(message = "Email name cannot be blank")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;
}
