package com.example.capstone1;

import java.sql.Time;

public class MedicationsModel {
    private String Medication;
    private String TimeMedication;
    private String InventoryMeds;

    private MedicationsModel(){}

    private MedicationsModel(String Medication,String TimeMedication, String InventoryMeds){
        this.Medication = Medication;
        this.TimeMedication = TimeMedication;
        this.InventoryMeds = InventoryMeds;

    }

    public String getMedication(){
        return Medication;
    }

    public void setMedication(String Medication){
        this.Medication = Medication;
    }

    public String getTimeMedication(){
        return TimeMedication;
    }

    public void setTimeMedication(String TimeMedication){
        this.TimeMedication = TimeMedication;
    }

    public String getInventoryMeds(){
        return InventoryMeds;
    }

    public void setInventoryMeds(String InventoryMeds){
        this.InventoryMeds = InventoryMeds;
    }
}