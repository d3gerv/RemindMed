package com.example.capstone1;

public class medication_info {
    String Medication, InventoryMeds, Dosage, StartDate, Time;

    public medication_info() {}

    public medication_info(String Medication, String InventoryMeds, String Dosage, String StartDate, String Time)
    {
        this.Medication = Medication;
        this.InventoryMeds = InventoryMeds;
        this.Dosage = Dosage;
        this.StartDate = StartDate;
        this.Time = Time;
    }

    public String getMedication() {
        return Medication;
    }

    public void setMedication(String Medication) {
        this.Medication = Medication;
    }

    public String getInventoryMeds() {
        return InventoryMeds;
    }

    public void setInventoryMeds(String InventoryMeds) {
        this.InventoryMeds = InventoryMeds;
    }

    public String getDosage() {
        return Dosage;
    }

    public void setDosage(String Dosage) {
        this.Dosage = Dosage;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String StartDate) {
        this.Time = Time;
    }



}
