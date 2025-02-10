package com.gideon.contact_manager.application.usecase.user;

import com.gideon.contact_manager.application.service.user.TokenValidationResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenValidationResultImpl extends TokenValidationResult {

    public TokenValidationResultImpl(String email, boolean isValid) {
        this.userEmail = email;
        this.iSValidToken = isValid;

    }
}
