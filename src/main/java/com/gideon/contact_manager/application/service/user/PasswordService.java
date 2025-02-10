package com.gideon.contact_manager.application.service.user;

public interface PasswordService {
    String ComputePasswordHash(String password);
    String GenerateForgotPasswordToken(String email);
    TokenValidationResult ValidateResetPasswordToken(String token);
}
