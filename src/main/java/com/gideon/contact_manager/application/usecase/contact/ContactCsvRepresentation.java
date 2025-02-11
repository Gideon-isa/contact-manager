package com.gideon.contact_manager.application.usecase.contact;

import com.gideon.contact_manager.domain.model.ContactGroup;
import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactCsvRepresentation {
    @CsvBindByName(column = "first name")
    private String firstName;

    @CsvBindByName(column = "last name")
    private String lastName;

    @CsvBindByName(column = "email")
    private String email;

    @CsvBindByName(column = "email")
    private String phoneNumber;

    @CsvBindByName(column = "contact image")
    private String imagurl;

    @CsvBindByName(column = "address")
    private String address;

    @CsvBindByName(column = "favourite")
    private Boolean isfavourite;
    @CsvBindByName(column = "contact group")
    private ContactGroup croup;
}
