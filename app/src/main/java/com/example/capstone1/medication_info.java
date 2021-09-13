package com.example.capstone1;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class medication_info implements Serializable {
    String Medication, InventoryMeds, Dosage, StartDate, Time ,EndDate;
    @Exclude  private String id;
    public medication_info() {}

    public medication_info(String Medication, String InventoryMeds,  String StartDate, String Time, String EndDate )
    {
        this.Medication = Medication;
        this.InventoryMeds = InventoryMeds;
        this.StartDate = StartDate;
        this.Time = Time;
        this.EndDate = EndDate;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }
}
