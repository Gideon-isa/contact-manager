//package com.gideon.contact_manager.applicationTest.usecaseTest.contactTest;
//
//import com.gideon.contact_manager.application.dto.ContactResponse;
//import com.gideon.contact_manager.application.mapper.ContactMapper;
//import com.gideon.contact_manager.application.usecase.contact.ContactServiceImpl;
//import com.gideon.contact_manager.domain.model.Contact;
//import com.gideon.contact_manager.infrastructure.persistence.JpaContactRepository;
//import com.gideon.contact_manager.presentation.apimodels.contact.CreateContactRequest;
//import com.gideon.contact_manager.presentation.apimodels.contact.UpdateContactRequest;
//import com.gideon.contact_manager.shared.BaseResponse;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.testng.AssertJUnit.assertEquals;
//
//@ExtendWith(MockitoExtension.class)
//public class ContactServiceImplTest {
//    @Mock
//    private JpaContactRepository jpaContactRepository;
//
//    @Mock
//    private ContactMapper contactMapper;
//
//    @InjectMocks
//    private ContactServiceImpl contactService;
//
//    private Contact contact;
//    private ContactResponse contactResponse;
//    private CreateContactRequest createContactRequest;
//    private UpdateContactRequest updateContactRequest;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        // Sample Data
//        contact = new Contact();
//        contact.setId(1L);
//        contact.setFirstName("John");
//        contact.setLastName("Doe");
//        contact.setEmail("john.doe@example.com");
//        contact.setPhoneNumber("1234567890");
//
//        contactResponse = new ContactResponse();
//        contactResponse.setId(1L);
//        contactResponse.setFirstName("John");
//        contactResponse.setLastName("Doe");
//
//        createContactRequest = new CreateContactRequest();
//        createContactRequest.setFirstName("John");
//        createContactRequest.setLastName("Doe");
//        createContactRequest.setEmail("john.doe@example.com");
//        createContactRequest.setPhoneNumber("1234567890");
//
//        updateContactRequest = new UpdateContactRequest();
//        updateContactRequest.setFirstName("Johnny");
//        updateContactRequest.setLastName("Doe");
//        updateContactRequest.setEmail("johnny.doe@example.com");
//        updateContactRequest.setPhoneNumber("0987654321");
//    }
//
//    @Test
//    public void testCreateContact() {
//        when(contactMapper.toEntity(any())).thenReturn(contact);
//        when(jpaContactRepository.save(any())).thenReturn(contact);
//        when(contactMapper.toDTO(any())).thenReturn(contactResponse);
//
//        BaseResponse<ContactResponse> response = contactService.createContact(createContactRequest);
//
//        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
//        assertEquals("contact created successfully", response.getMessage());
//        assertEquals("John", response.getData().getFirstName());
//
//        verify(jpaContactRepository, times(1)).save(any(Contact.class));
//    }
//
//    @Test
//    public void testGetContact_Success() {
//        when(jpaContactRepository.findById(1L)).thenReturn(Optional.of(contact));
//        when(contactMapper.toDTO(contact)).thenReturn(contactResponse);
//
//        BaseResponse<ContactResponse> response = contactService.getContact(1L);
//
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertEquals("User found", response.getMessage());
//        assertNotNull(response.getData());
//        assertEquals(Optional.of(1L), response.getData().getId());
//
//        verify(jpaContactRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    public void testGetContact_NotFound() {
//        when(jpaContactRepository.findById(1L)).thenReturn(Optional.empty());
//
//        BaseResponse<ContactResponse> response = contactService.getContact(1L);
//
//        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
//        assertEquals("User does not exist", response.getMessage());
//        assertNull(response.getData());
//
//        verify(jpaContactRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    public void testUpdateContact_Success() {
//        when(jpaContactRepository.findById(1L)).thenReturn(Optional.of(contact));
//        when(jpaContactRepository.save(any())).thenReturn(contact);
//        when(contactMapper.toDTO(any())).thenReturn(contactResponse);
//
//        BaseResponse<ContactResponse> response = contactService.updateContact(1L, updateContactRequest);
//
//        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
//        assertEquals("contact updated successfully", response.getMessage());
//
//        verify(jpaContactRepository, times(1)).findById(1L);
//        verify(jpaContactRepository, times(1)).save(any(Contact.class));
//    }
//
//    @Test
//    public void testUpdateContact_NotFound() {
//        when(jpaContactRepository.findById(1L)).thenReturn(Optional.empty());
//
//        BaseResponse<ContactResponse> response = contactService.updateContact(1L, updateContactRequest);
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
//        assertEquals("Error updating the contact", response.getMessage());
//
//        verify(jpaContactRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    public void testDeleteContact_Success() {
//        when(jpaContactRepository.findById(1L)).thenReturn(Optional.of(contact));
//
//        BaseResponse<ContactResponse> response = contactService.deleteContact(1L);
//
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertEquals("user with id 1 has been deleted", response.getMessage());
//
//        verify(jpaContactRepository, times(1)).deleteById(1L);
//    }
//
//    @Test
//    public void testDeleteContact_NotFound() {
//        when(jpaContactRepository.findById(1L)).thenReturn(Optional.empty());
//
//        BaseResponse<ContactResponse> response = contactService.deleteContact(1L);
//
//        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
//        assertEquals("User does not exist", response.getMessage());
//
//        verify(jpaContactRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    public void testDeleteMultipleContacts_Success() {
//        List<Long> ids = Arrays.asList(1L, 2L, 3L);
//        doNothing().when(jpaContactRepository).deleteAllById(ids);
//
//        BaseResponse<ContactResponse> response = contactService.deleteMultipleContacts(ids);
//
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertEquals("selected user deleted successfully", response.getMessage());
//
//        verify(jpaContactRepository, times(1)).deleteAllById(ids);
//    }
//
//    @Test
//    public void testDeleteMultipleContacts_Error() {
//        List<Long> ids = Arrays.asList(1L, 2L, 3L);
//        doThrow(new RuntimeException("Deletion failed")).when(jpaContactRepository).deleteAllById(ids);
//
//        BaseResponse<ContactResponse> response = contactService.deleteMultipleContacts(ids);
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
//        assertEquals("error deleting", response.getMessage());
//
//        verify(jpaContactRepository, times(1)).deleteAllById(ids);
//    }
//}
