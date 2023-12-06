package com.bloodbank.bloodbank.dto.validation.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dates {
    private LocalDate dob;
    private LocalDate employed;
}
