package com.gideon.contact_manager.presentation.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        try{
            String password = BeanUtils.getProperty(obj, "password");
            String confirmPassword = BeanUtils.getProperty(obj, "confirmPassword");
            return password.equals(confirmPassword);
        } catch (Exception e) {
            return false;
        }
    }
}
