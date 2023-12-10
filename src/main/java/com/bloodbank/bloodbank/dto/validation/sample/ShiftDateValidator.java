package com.bloodbank.bloodbank.dto.validation.sample;

import com.bloodbank.bloodbank.service.bank.ShiftService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class ShiftDateValidator implements ConstraintValidator<ShiftDate, LocalDate> {
    @Autowired
    private ShiftService service;
    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        return service.findByDate(date) != null;
    }
}
