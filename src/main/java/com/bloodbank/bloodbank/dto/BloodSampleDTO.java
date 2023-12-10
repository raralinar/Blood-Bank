package com.bloodbank.bloodbank.dto;


import com.bloodbank.bloodbank.dto.validation.sample.IsDonorAllowed;
import com.bloodbank.bloodbank.dto.validation.sample.ShiftDate;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class BloodSampleDTO {
    private Long id;
    @NotNull(message = "Значение 'Время смены' не должно быть пустым.")
    private LocalTime time = LocalTime.now();
    @Min(value = 0,message = "Значение 'Объем' не может быть меньше 0 мл.")
    @Max(value = 500,message = "Значение 'Объем' не может быть больше 500 мл.")
    private Integer volume;
    @NotNull
    @NotBlank(message = "Значение 'Донор' не должно быть пустым.")
    @IsDonorAllowed
    private String donor;
    @ShiftDate
    @NotNull(message = "Значение 'Дата смены' не должно быть пустым.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date = LocalDate.now();
    private String employee;
}
