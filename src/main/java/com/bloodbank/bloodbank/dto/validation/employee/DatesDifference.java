package com.bloodbank.bloodbank.dto.validation.employee;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmployeeDatesValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DatesDifference {

    String message() default "Разница между датами должна быть не менее 16 лет.";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}
