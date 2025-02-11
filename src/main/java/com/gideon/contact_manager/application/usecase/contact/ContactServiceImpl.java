package com.gideon.contact_manager.application.usecase.contact;

import com.gideon.contact_manager.application.dto.ContactResponse;
import com.gideon.contact_manager.application.features.contact.commands.CreateContactCommand;
import com.gideon.contact_manager.application.mapper.ContactMapper;
import com.gideon.contact_manager.application.mapper.UserMapper;
import com.gideon.contact_manager.application.service.contact.ContactService;
import com.gideon.contact_manager.domain.model.Contact;
import com.gideon.contact_manager.domain.repository.ContactRepository;
import com.gideon.contact_manager.infrastructure.persistence.JpaContactRepository;
import com.gideon.contact_manager.presentation.apimodels.CreateContactRequest;
import com.gideon.contact_manager.presentation.apimodels.ImportContactRequest;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final JpaContactRepository jpaContactRepository;
    private final ContactMapper contactMapper;

    @Override
    public BaseResponse<ContactResponse> createContact(CreateContactRequest request) {
        return null;
    }

    @Override
    public BaseResponse<ContactResponse> updateContact(CreateContactRequest request) {
        return null;
    }

    @Override
    public BaseResponse<ContactResponse> deleteContact(CreateContactRequest request) {
        return null;
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
                        .contactImage(createRequest.getContactImage())
                        .physicalAddress(createRequest.getAddress())
//                        .group(String.valueOf(createRequest.getGroup()))
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
}
