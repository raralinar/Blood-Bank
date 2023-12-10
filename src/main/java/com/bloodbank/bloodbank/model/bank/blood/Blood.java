package com.bloodbank.bloodbank.model.bank.blood;

import com.bloodbank.bloodbank.model.bank.Donor;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "blood")
@IdClass(BloodID.class)
public class Blood implements Serializable {
    @Id
    private Integer blood_type;
    @Id
    private String rhesus;
    @Id
    private String kell;
    private int volume;

    @Transient
    @OneToMany(mappedBy = "blood")
    private List<Donor> donor;

    public int getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(int blood_type) {
        this.blood_type = blood_type;
    }

    public String getRhesus() {
        return rhesus;
    }

    public void setRhesus(String rhesus) {
        this.rhesus = rhesus;
    }

    public String getKell() {
        return kell;
    }

    public void setKell(String kell) {
        this.kell = kell;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
