//package com.bloodbank.bloodbank.dto.validation.employee;
//
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import org.springframework.beans.BeanWrapperImpl;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Calendar;
//import java.util.Locale;
//
//import static java.util.Calendar.*;
//
//public class EmployeeDatesValidator implements ConstraintValidator<DatesDifference, Object>  {
//    private String[] fields;
//
//    public void initialize(DatesDifference constraintAnnotation) {
//        this.fields = constraintAnnotation.value();
//    }
//
//    @Override
//    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
//        System.out.println(fields[0]);
//        Object fieldValue = new BeanWrapperImpl(value)
//                .getPropertyValue(fields[0]);
//        Object fieldMatchValue = new BeanWrapperImpl(value)
//                .getPropertyValue(fields[1]);
//        if (fieldValue == null || fieldMatchValue == null)
//            return false;
//        LocalDate field = getDate(String.valueOf(fieldValue));
//        LocalDate fieldMatch = getDate(String.valueOf(fieldMatchValue));
//        System.out.println(getDiffYears(field, fieldMatch));
//        System.out.println(field);
//        return getDiffYears(field, fieldMatch) >= 16;
//    }
//
//    public static LocalDate getDate(String s) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
//        formatter = formatter.withLocale(Locale.getDefault() );  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
//        LocalDate date = LocalDate.parse(s, formatter);
//        return date;
//    }
//
//    public static int getDiffYears(LocalDate first, LocalDate last) {
//        Calendar a = getCalendar(first);
//        Calendar b = getCalendar(last);
//        int diff = b.get(YEAR) - a.get(YEAR);
//        if (a.get(MONTH) > b.get(MONTH) ||
//                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
//            diff--;
//        }
//        return diff;
//    }
//
//    public static Calendar getCalendar(LocalDate date) {
//        return Calendar.getInstance(Locale.US);
//    }
//}


package com.bloodbank.bloodbank.dto.validation.employee;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Locale;

public class EmployeeDatesValidator implements ConstraintValidator<DatesDifference, Dates>  {

    public void initialize(DatesDifference constraintAnnotation) {
    }

    @Override
    public boolean isValid(Dates value, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(value.getDob());
        System.out.println(getDiffYears(value.getDob(), value.getEmployed()));
        return getDiffYears(value.getDob(), value.getEmployed());
    }

    public static boolean getDiffYears(LocalDate first, LocalDate last) {
        return ChronoUnit.YEARS.between(first, last) >= 16;
    }

    public static Calendar getCalendar(LocalDate date) {
        return Calendar.getInstance(Locale.US);
    }
}
