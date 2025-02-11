package com.gideon.contact_manager.application.usecase.contact;

import com.gideon.contact_manager.application.dto.ContactResponse;
import com.gideon.contact_manager.application.dto.UserResponse;
import com.gideon.contact_manager.application.features.contact.commands.CreateContactCommand;
import com.gideon.contact_manager.application.features.contact.commands.ImportContactCommand;
import com.gideon.contact_manager.application.features.contact.commands.UpdateContactCommand;
import com.gideon.contact_manager.application.mapper.ContactMapper;
import com.gideon.contact_manager.application.service.contact.ContactService;
import com.gideon.contact_manager.domain.model.Contact;
import com.gideon.contact_manager.domain.model.User;
import com.gideon.contact_manager.infrastructure.persistence.ContactSpecificationSearch;
import com.gideon.contact_manager.infrastructure.persistence.JpaContactRepository;
import com.gideon.contact_manager.presentation.apimodels.contact.CreateContactRequest;
import com.gideon.contact_manager.presentation.apimodels.contact.ImportContactRequest;
import com.gideon.contact_manager.presentation.apimodels.contact.UpdateContactRequest;
import com.gideon.contact_manager.shared.BaseResponse;
import com.gideon.contact_manager.shared.Error;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final JpaContactRepository jpaContactRepository;
    private final ContactMapper contactMapper;

    @Override
    public BaseResponse<ContactResponse> createContact(CreateContactRequest request) {
        String imagePath = SaveContactImage.saveContactImage(request.getContactImage());
        CreateContactCommand cmd = CreateContactCommand
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .contactImage(imagePath)
                .physicalAddress(request.getAddress())
                .isFavourite(request.isFavourite())
                .group(request.getGroup().toString())
                .createdOn(Date.from(Instant.now()))
                .createdBy(request.getFirstName())
                .build();

        Contact newContact = contactMapper.toEntity(cmd);
        jpaContactRepository.save(newContact);
        ContactResponse contactDto = contactMapper.toDTO(newContact);
        return BaseResponse.
                success(contactDto, HttpStatus.CREATED.value(),
                        "contact created successfully");

    }

    @Override
    public BaseResponse<ContactResponse> updateContact(Long id, UpdateContactRequest request) {
         Optional<Contact> contact =  jpaContactRepository.findById(id);
         if(contact.isPresent()) {
             String imagePath = SaveContactImage.saveContactImage(request.getContactImage());
             Contact toUpdateContact = contact.get();

             toUpdateContact.setFirstName(request.getFirstName());
             toUpdateContact.setLastName(request.getLastName());
             toUpdateContact.setEmail(request.getEmail());
             toUpdateContact.setPhoneNumber(request.getPhoneNumber());
             toUpdateContact.setContactImage(imagePath);
             toUpdateContact.setAddress(request.getAddress());
             toUpdateContact.setIsFavourite(request.getIsFavourite());
             toUpdateContact.setContactGroup(request.getGroup());
             toUpdateContact.setModifiedOn(Date.from(Instant.now()));
             toUpdateContact.setModifiedBy(request.getFirstName());

             jpaContactRepository.save(toUpdateContact);
             ContactResponse contactDto = contactMapper.toDTO(toUpdateContact);
             return BaseResponse.
                     success(contactDto, HttpStatus.CREATED.value(),
                             "contact updated successfully");
         }
        return BaseResponse.failure(new ContactResponse(),
                new Error("500", "Error updating the contact"),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error updating the contact");
    }

    @Override
    public BaseResponse<ContactResponse> getContact(Long id) {
        Optional<Contact> contact = jpaContactRepository.findById(id);
        if(contact.isPresent()) {
            ContactResponse contactDto = contact.map(contactMapper::toDTO).orElse(null);
            return BaseResponse.success(contactDto, HttpStatus.OK.value(), "User found");
        }
        return BaseResponse.failure(new ContactResponse(),
                new Error("404", "No user of such exist in the application"),
                HttpStatus.NO_CONTENT.value(), "User does not exist");
    }

    @Override
    public BaseResponse<ContactResponse> deleteContact(Long id) {
        var contact = getContact(id);
        if(contact.getData() != null) {
            jpaContactRepository.deleteById(id);
            return BaseResponse.success(contact.getData(), HttpStatus.OK.value(),
                    String.format("user with id %d has been deleted", contact.getData().id));
        };
        return BaseResponse.failure(new ContactResponse(),
                new Error("404", "user does not exist in the system"),
                HttpStatus.NO_CONTENT.value(), "User does not exist");
    }

    @Override
    public BaseResponse<ContactResponse> deleteMultipleContacts(List<Long> ids) {
        try {
            jpaContactRepository.deleteAllById(ids);
            return BaseResponse.success(new ContactResponse(), HttpStatus.OK.value(),
                    "selected user deleted successfully");
        } catch (RuntimeException e) {
            return BaseResponse.failure(new ContactResponse(),
                    new Error("404", e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "error deleting");
        }
    }

    @Override
    public BaseResponse<Integer> importContacts(ImportContactRequest request) {
        try {
            Set<CreateContactRequest> createContactRequestSet = parseCsv((MultipartFile) request.getFile());
            List<Contact> contactList = new ArrayList<>();
            for (CreateContactRequest createRequest : createContactRequestSet) {
                CreateContactCommand cmd = CreateContactCommand
                        .builder()
                        .firstName(createRequest.getFirstName())
                        .lastName(createRequest.getLastName())
                        .email(createRequest.getEmail())
                        .phoneNumber(createRequest.getPhoneNumber())
                        .contactImage(createRequest.getContactImage().toString())
                        .physicalAddress(createRequest.getAddress())
                        .group(String.valueOf(createRequest.getGroup()))
                        .createdOn(Date.from(Instant.now()))
                        .createdBy(createRequest.getFirstName())
                        .build();
                Contact newContact = contactMapper.toEntity(cmd);
                contactList.add(newContact);
            }
            jpaContactRepository.saveAll(contactList);
            return BaseResponse.success(contactList.size(), HttpStatus.OK.value(), "contacts created");
        }catch (IOException e) {
            return BaseResponse.failure(0,
                    new Error("500", "error parsing the CSV"),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "unable to parse the file");
        }
    }

    private Set<CreateContactRequest> parseCsv(MultipartFile file) throws IOException {
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<ContactCsvRepresentation> strategy =
                    new HeaderColumnNameMappingStrategy<ContactCsvRepresentation>();
            strategy.setType(ContactCsvRepresentation.class);
            CsvToBean<ContactCsvRepresentation> csvToBean =
                    new CsvToBeanBuilder<ContactCsvRepresentation>(reader)
                            .withMappingStrategy(strategy)
                            .withIgnoreEmptyLine(true)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();
            return csvToBean.parse()
                    .stream()
                    .map(csvLine -> CreateContactRequest.builder()
                            .firstName(csvLine.getFirstName())
                            .lastName(csvLine.getLastName())
                            .email(csvLine.getEmail())
                            .address(csvLine.getAddress())
                            .phoneNumber(csvLine.getPhoneNumber()).build())
                    .collect(Collectors.toSet());
        }
    }

    public BaseResponse<ContactResponse> exportContactsToCsv(HttpServletResponse response)  {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=contacts.csv");

        try (PrintWriter writer = response.getWriter(); CSVWriter csvWriter = new CSVWriter(writer)) {
            // Write CSV header
            String[] header = {"first Name", "last name", "email", "phone number", "address"};
            csvWriter.writeNext(header);
            // Fetch data
            List<Contact> contacts = jpaContactRepository.findAll();

            // Write each row
            for (Contact contact : contacts) {
                String[] row = {
                        contact.getFirstName(),
                        contact.getLastName(),
                        contact.getEmail(),
                        contact.getPhoneNumber(),
                        contact.getAddress()
                };
                csvWriter.writeNext(row);
            }
            return BaseResponse.success(new ContactResponse(), HttpStatus.OK.value(), "Success");
        }catch (IOException e) {
            return BaseResponse.failure(new ContactResponse(),
                    new Error("500", "Error writing the CSV file"),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error during export");
        }
    }

    @Override
    public BaseResponse<List<ContactResponse>> searchContact(String searchTerm) {

        List<ContactResponse> contactList = jpaContactRepository
                .findAll(ContactSpecificationSearch.searchContacts(searchTerm))
                .stream()
                .map(contactMapper::toDTO)
                .toList();
        int totalUsers = contactList.size();
        return BaseResponse.success(contactList, HttpStatus.OK.value(), String.format("returned %d user", totalUsers));
    }
}
