package com.example.capstone1;

public class MedicationsModel {
    private String Medication;
    private String Dosage;
    private String InventoryMeds;

    private MedicationsModel(){}

    private MedicationsModel(String Medication,String Dosage, String InventoryMeds){
        this.Medication = Medication;
        this.Dosage = Dosage;
        this.InventoryMeds = InventoryMeds;

    }

    public String getMedication(){
        return Medication;
    }

    public void setMedication(String Medication){
        this.Medication = Medication;
    }

    public String getDosage(){
        return Dosage;
    }

    public void setDosage(String Dosage){
        this.Dosage = Dosage;
    }

    public String getInventoryMeds(){
        return InventoryMeds;
    }

    public void setInventoryMeds(String InventoryMeds){
        this.InventoryMeds = InventoryMeds;
    }
}