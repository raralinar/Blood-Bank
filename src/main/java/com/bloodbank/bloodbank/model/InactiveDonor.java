package com.bloodbank.bloodbank.model;


import com.bloodbank.bloodbank.model.blood.Blood;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "inactive_patient")
public class InactiveDonor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String surname;
    @Column(name = "donor_name")
    private String name;
    private String patronymic;
    private String sex;
    private String phone;
    @Column(name = "birth")
    private LocalDate dob;
    private int donation;
    private String reason;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "blood_type", referencedColumnName = "blood_type"),
            @JoinColumn(name = "rhesus", referencedColumnName = "rhesus"),
            @JoinColumn(name = "kell", referencedColumnName = "kell")})
    private Blood blood;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public Blood getBlood() {
        return blood;
    }

    public void setBlood(Blood blood) {
        this.blood = blood;
    }

    public int getDonation() {
        return donation;
    }

    public void setDonation(int donation) {
        this.donation = donation;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
