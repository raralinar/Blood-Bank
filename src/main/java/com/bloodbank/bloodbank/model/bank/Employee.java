package com.bloodbank.bloodbank.model.bank;

import jakarta.persistence.*;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table
@ToString
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public LocalDate getEmployed() {
        return employed;
    }

    public void setEmployed(LocalDate employed) {
        this.employed = employed;
    }
}
