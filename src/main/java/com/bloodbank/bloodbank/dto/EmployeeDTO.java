package com.bloodbank.bloodbank.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    @NotNull
    @NotEmpty(message = "Значение 'Имя' не должно быть пустым.")
    private String name;
    @NotNull
    @NotBlank(message = "Значение 'Фамилия' не должно быть пустым.")
    private String surname;
    private String patronymic;
    @NotBlank(message = "Значение 'Телефон' не должно быть пустым.")
    @NotNull
//    @UniquePhone
    @Pattern(regexp = "^(8)([9]{1}[0-9]{9})?$", message = "Значение 'Телефон' не соответствует формату.")
    private String phone;
    @NotNull(message = "Значение 'Дата' не должно быть пустым.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    @NotNull(message = "Значение 'Дата устройства' не должно быть пустым.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate employed;
    @AssertFalse(message = "Разница между датами должна быть не менее 16 лет.")
    private Boolean difference;
}
