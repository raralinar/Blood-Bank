package com.bloodbank.bloodbank.dto.validation.sample;

import com.bloodbank.bloodbank.model.bank.BloodSample;
import com.bloodbank.bloodbank.service.bank.BloodSampleService;
import com.bloodbank.bloodbank.service.bank.DonorService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DonorAllowanceValidator implements ConstraintValidator<IsDonorAllowed, String> {
    @Autowired
    private DonorService donorService;
    @Autowired
    private BloodSampleService bloodSampleService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.isBlank())
            return true;
        List<BloodSample> list = bloodSampleService.findByDonor(Long.parseLong(s.split(" ")[0]));
        if (list.isEmpty())
            return true;
        LocalDate date = list.stream()
                .map(sample -> sample.getShift().getDate()).filter(date1 -> date1.getYear() == LocalDate.now().getYear()).max(LocalDate::compareTo).get();
        return ChronoUnit.DAYS.between(date, LocalDate.now()) >= 60;
    }
}
