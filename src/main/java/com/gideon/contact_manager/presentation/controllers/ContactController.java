package com.gideon.contact_manager.presentation.controllers;

import com.gideon.contact_manager.application.dto.ContactResponse;
import com.gideon.contact_manager.application.service.contact.ContactService;
import com.gideon.contact_manager.presentation.apimodels.contact.CreateContactRequest;
import com.gideon.contact_manager.presentation.apimodels.contact.ImportContactRequest;
import com.gideon.contact_manager.presentation.apimodels.contact.UpdateContactRequest;
import com.gideon.contact_manager.shared.BaseResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/contact")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<ContactResponse>> createContact(
            @RequestPart("request") @Valid CreateContactRequest request,
            @RequestPart(value = "contactImage", required = false) MultipartFile contactImage) {

        request.setContactImage(contactImage);
        var response = contactService.createContact(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<ContactResponse>> updateContact (@PathVariable Long id,
           @RequestPart("request") @Valid UpdateContactRequest request,
           @RequestPart(value = "contactImage", required = false) MultipartFile contactImage) {

        request.setContactImage(contactImage);
        var response = contactService.updateContact(id,request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ContactResponse>> getContactById(@PathVariable Long id) {
        log.info("retrieving contact with ID: {}", id);
        BaseResponse<ContactResponse> response = contactService.getContact(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping(value = "/import", consumes = {"multipart/form-data"})
    public ResponseEntity<BaseResponse<Integer>> uploadContacts(@RequestBody ImportContactRequest request) throws IOException {
        var response = contactService.importContacts(request);
        return  ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/export")
    public ResponseEntity<BaseResponse<ContactResponse>> exportContacts(HttpServletResponse response) {
        var responseResult = contactService.exportContactsToCsv(response);
        return ResponseEntity.status(responseResult.getStatus()).body(responseResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<ContactResponse>> deleteContactById(@PathVariable Long id) {
        log.info("deleting contact with ID: {}", id);
        BaseResponse<ContactResponse> response = contactService.deleteContact(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/")
    public ResponseEntity<BaseResponse<ContactResponse>> deleteContactById(@RequestBody List<Long> ids) {
        log.info("deleting selected contacts");
        BaseResponse<ContactResponse> response = contactService.deleteMultipleContacts(ids);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
