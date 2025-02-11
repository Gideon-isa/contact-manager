package com.gideon.contact_manager.presentation.controllers;

import com.gideon.contact_manager.application.dto.ContactResponse;
import com.gideon.contact_manager.application.service.contact.ContactService;
import com.gideon.contact_manager.application.service.user.UserService;
import com.gideon.contact_manager.domain.model.Contact;
import com.gideon.contact_manager.presentation.apimodels.ImportContactRequest;
import com.gideon.contact_manager.shared.BaseResponse;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;
    //@RequestPart("file")MultipartFile file

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<BaseResponse<Integer>> uploadContacts(@RequestBody ImportContactRequest request) throws IOException {
        var response = contactService.importContacts(request);
        return  ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/export")
    public ResponseEntity<BaseResponse<ContactResponse>> exportContacts(HttpServletResponse response) {
        var responseResult = contactService.exportContactsToCsv(response);
        return ResponseEntity.status(responseResult.getStatus()).body(responseResult);
    }
}
