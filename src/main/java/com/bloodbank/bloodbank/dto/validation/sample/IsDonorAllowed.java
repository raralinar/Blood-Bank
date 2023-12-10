package com.bloodbank.bloodbank.dto.validation.sample;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DonorAllowanceValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsDonorAllowed {
    String message() default "С момента последней донации не прошло 60 дней!";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
