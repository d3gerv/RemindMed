package com.example.capstone1;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class medication_info implements Serializable {
    String Medication, InventoryMeds, Dosage, Time;
    Date StartDate, EndDate;
    int MedicineType, Frequency;
    @Exclude  private String id;
    public medication_info() {}



    public medication_info(String Medication, String InventoryMeds, Date StartDate, String Time, Date EndDate )
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

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String StartDate) {
        this.Time = Time;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public int getMedicineType() {
        return MedicineType;
    }

    public void setMedicineType(int medicineType) {
        MedicineType = medicineType;
    }

    public int getFrequency() {
        return Frequency;
    }

    public void setFrequency(int frequency) {
        Frequency = frequency;
    }
}
