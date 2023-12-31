package com.bloodbank.bloodbank.dto;

import com.bloodbank.bloodbank.dto.validation.donor.UniquePhone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DonorDTO {
    private Long id;
    @NotNull
    @NotEmpty(message = "Значение 'Имя' не должно быть пустым.")
    private String name;
    @NotNull
    @NotBlank(message = "Значение 'Фамилия' не должно быть пустым.")
    private String surname;
    private String patronymic;
    @NotNull(message = "Значение 'Пол' не должно быть пустым.")
    @NotEmpty(message = "Значение 'Пол' не должно быть пустым.")
    private String sex;
    @NotBlank(message = "Значение 'Телефон' не должно быть пустым.")
    @NotNull
    @UniquePhone
    @Pattern(regexp = "^(\\+7)(\\s\\()([9]{1}[0-9]{2}\\)\\s[0-9]{3}\\s[0-9]{2}\\s[0-9]{2})?$", message = "Значение 'Телефон' не соответствует формату.")
    private String phone;
    @NotNull(message = "Значение 'Дата' не должно быть пустым.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    private Integer donations = 0;
    @NotNull(message = "Значение 'Группа крови' не должно быть пустым.")
    private Integer blood_type;
    @NotBlank(message = "Значение 'Резус-фактора' не должно быть пустым.")
    @NotNull
    private String rhesus;
}
