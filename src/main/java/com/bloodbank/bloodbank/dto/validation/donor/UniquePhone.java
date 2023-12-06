package com.bloodbank.bloodbank.dto.validation.donor;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Constraint(validatedBy = UniquePhoneValidator.class)
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePhone {
    String message() default "Такой номер телефона уже зарегистрирован";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
