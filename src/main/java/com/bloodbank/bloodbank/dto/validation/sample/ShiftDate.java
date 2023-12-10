package com.bloodbank.bloodbank.dto.validation.sample;

import com.bloodbank.bloodbank.dto.validation.donor.UniquePhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Constraint(validatedBy = ShiftDateValidator.class)
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ShiftDate {
    String message() default "Нет зарегистрированной смены в этот день!";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
