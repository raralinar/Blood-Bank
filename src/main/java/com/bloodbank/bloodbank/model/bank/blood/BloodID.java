package com.bloodbank.bloodbank.model.blood;

import java.io.Serializable;

public class BloodID implements Serializable {
    private int blood_type;
    private String rhesus;
    private String kell;

    public BloodID() {
    }

    public BloodID(int blood_type, String rhesus, String kell) {
        this.blood_type = blood_type;
        this.rhesus = rhesus;
        this.kell = kell;
    }

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
}
