package com.gideon.contact_manager.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "first name cannot be blank")
    @NonNull
    @Column(nullable = false, unique = true)
    private String firstName;

    @NotBlank(message = "last name cannot be blank")
    @NonNull
    @Column(nullable = false, unique = true)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "phone number cannot be blank")
    @NonNull
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    private String imageUrl;

    @NotBlank(message = "address cannot be blank")
    @NonNull
    @Column(nullable = false, unique = true)
    private String address;

    @Enumerated(EnumType.STRING)
    private ContactGroup group;
}
