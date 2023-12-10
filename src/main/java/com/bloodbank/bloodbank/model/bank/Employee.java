package com.bloodbank.bloodbank.model.bank;

import com.bloodbank.bloodbank.model.login.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String surname;
    @Column(name = "employee_name")
    private String name;
    private String patronymic;
    private String phone;
    @Column(name = "birth")
    private LocalDate dob;
    private LocalDate employed;

    @Transient
    @OneToOne(mappedBy = "employee")
    private Shift shift;

    @Transient
    @OneToOne(mappedBy = "employee", cascade = CascadeType.REMOVE)
    private User user;
    private String email;
    private String password;
}
