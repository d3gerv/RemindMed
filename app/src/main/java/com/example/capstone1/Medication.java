package com.example.capstone1;

import android.app.Application;

public class Medication extends Application {
    private String medication, inventory, dosage;

    public Medication(String medication, String dosage, String inventory) {
        this.medication = medication;
        this.dosage = dosage;
        this.inventory = inventory;
    }

    public Medication(String dosage, String inventory) {
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }
}
