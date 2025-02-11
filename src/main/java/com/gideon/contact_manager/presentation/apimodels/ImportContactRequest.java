package com.gideon.contact_manager.presentation.apimodels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

@Getter
@Setter
public class ImportContactRequest {
    StandardMultipartHttpServletRequest file;
}
