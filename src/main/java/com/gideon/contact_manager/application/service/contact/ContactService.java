package com.gideon.contact_manager.application.service.contact;

import com.gideon.contact_manager.application.dto.ContactResponse;
import com.gideon.contact_manager.presentation.apimodels.contact.CreateContactRequest;
import com.gideon.contact_manager.presentation.apimodels.contact.ImportContactRequest;
import com.gideon.contact_manager.presentation.apimodels.contact.UpdateContactRequest;
import com.gideon.contact_manager.shared.BaseResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface ContactService {
    BaseResponse<ContactResponse> createContact(CreateContactRequest request);
    BaseResponse<ContactResponse> updateContact(Long id, UpdateContactRequest request);
    BaseResponse<ContactResponse> getContact(Long id);
    BaseResponse<ContactResponse> deleteContact(Long id);
    BaseResponse<ContactResponse> deleteMultipleContacts(List<Long> id);
    BaseResponse<Integer> importContacts(ImportContactRequest request) throws IOException;
    BaseResponse<ContactResponse> exportContactsToCsv(HttpServletResponse response);
    BaseResponse<List<ContactResponse>> searchContact(String searchTerm);
}
