package com.gideon.contact_manager.domain.model;

import com.gideon.contact_manager.domain.Primitives.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "phone_contacts")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Contact extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "first name cannot be blank")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "last name cannot be blank")
    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = true)
    private String email;

    @NotBlank(message = "phone number cannot be blank")
    @Column(nullable = true, unique = true)
    private String phoneNumber;

    private String contactImage;

    @NotBlank(message = "address cannot be blank")
    @Column(nullable = true, unique = true)
    private String address;

    private Boolean isFavourite;

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_group", nullable = true, columnDefinition = "VARCHAR(255)")
    private ContactGroup contactGroup;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;
}
