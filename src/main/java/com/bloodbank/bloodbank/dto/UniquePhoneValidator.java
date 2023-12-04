package com.bloodbank.bloodbank.dto;

import com.bloodbank.bloodbank.service.bank.DonorService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniquePhoneValidator  implements ConstraintValidator<UniquePhone, String> {
    @Autowired
    DonorService donorService;

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        return !donorService.isPhoneExists(phone);
    }
}
