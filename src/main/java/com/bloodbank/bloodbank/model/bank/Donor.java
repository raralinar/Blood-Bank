package com.bloodbank.bloodbank.model.bank;

import com.bloodbank.bloodbank.model.bank.blood.Blood;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "patient")
@NoArgsConstructor
@ToString
public class Donor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String surname;
    @Column(name = "donor_name")
    private String name;
    private String patronymic;
    private String sex;
    private String phone;
    @Column(name = "birth")
    private LocalDate dob;
    private int donation;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "blood_type", referencedColumnName = "blood_type"),
            @JoinColumn(name = "rhesus", referencedColumnName = "rhesus"),
            @JoinColumn(name = "kell", referencedColumnName = "kell")})
    private Blood blood;

    @Transient
    @OneToMany(mappedBy = "donor")
    private BloodSample bloodSample;


    public Blood getBlood() {
        return blood;
    }

    public void setBlood(Blood blood) {
        this.blood = blood;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getDonation() {
        return donation;
    }

    public void setDonation(int donation) {
        this.donation = donation;
    }

    public boolean isFull() {
        if (donation == 5 && sex.equals("m"))
            return true;
        return donation == 4 && sex.equals("f");
    }

    @Transient
    public String fullNameDonor() {
        return getId() + " " + getSurname() + " " + getName() + " " + getPatronymic();
    }

}
