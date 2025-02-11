package com.gideon.contact_manager.application.service.contact;

import com.gideon.contact_manager.application.dto.ContactResponse;
import com.gideon.contact_manager.application.dto.UserResponse;
import com.gideon.contact_manager.presentation.apimodels.CreateContactRequest;
import com.gideon.contact_manager.presentation.apimodels.CreateUserRequest;
import com.gideon.contact_manager.presentation.apimodels.ImportContactRequest;
import com.gideon.contact_manager.shared.BaseResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ContactService {
    BaseResponse<ContactResponse> createContact(CreateContactRequest request);
    BaseResponse<ContactResponse> updateContact(CreateContactRequest request);
    BaseResponse<ContactResponse> deleteContact(CreateContactRequest request);
    BaseResponse<Integer> importContacts(ImportContactRequest request) throws IOException;
    BaseResponse<ContactResponse> exportContactsToCsv(HttpServletResponse response);
}
