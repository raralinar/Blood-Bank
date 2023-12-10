package com.bloodbank.bloodbank.dto;


import com.bloodbank.bloodbank.model.bank.Employee;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShiftDTO {
    private Long id;
    @NotNull(message = "Значение 'Дата' не должно быть пустым.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @NotNull(message = "Значение 'Время начала смены' не должно быть пустым.")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime begin;
    @NotNull(message = "Значение 'Время окончания смены' не должно быть пустым.")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime end;
    @NotNull
    @NotBlank(message = "Значение 'ФИО работника' не должно быть пустым.")
    private String employee;
}
