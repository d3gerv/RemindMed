package com.example.capstone1;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class medication_info implements Serializable {
    String Medication, InventoryMeds, Dosage, Time, FrequencyName, MedicineTypeName, NotifyChoice;
    Date StartDate, EndDate;
    int MedicineType, Frequency, PillStatic, Hour, Minute, AlarmID;
    @Exclude  private String id;
    public medication_info() {}



    public medication_info(String Medication, String InventoryMeds, Date StartDate,
                           String Time, Date EndDate, String MedicineTypeMame,
                           String FrequencyName, int Frequency, int Hour, int Minute, int AlarmID, String Dosage, String NotifyChoice )
    {
        this.Medication = Medication;
        this.InventoryMeds = InventoryMeds;
        this.StartDate = StartDate;
        this.Time = Time;
        this.EndDate = EndDate;
        this.MedicineTypeName = MedicineTypeMame;
        this.FrequencyName = FrequencyName;
        this.Frequency = Frequency;
        this.Hour = Hour;
        this.Minute = Minute;
        this.AlarmID = AlarmID;
        this.Dosage =Dosage;
        this. NotifyChoice = NotifyChoice;


        this.id = id;
    }

    public String getNotifyChoice() {
        return NotifyChoice;
    }

    public void setNotifyChoice(String notifyChoice) {
        NotifyChoice = notifyChoice;
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

    public int getPillStatic() {
        return PillStatic;
    }

    public void setPillStatic(int pillStatic) {
        PillStatic = pillStatic;
    }

    public String getFrequencyName() {
        return FrequencyName;
    }

    public void setFrequencyName(String frequencyName) {
        FrequencyName = frequencyName;
    }

    public String getMedicineTypeName() {
        return MedicineTypeName;
    }

    public void setMedicineTypeName(String medicineTypeName) {
        MedicineTypeName = medicineTypeName;
    }
    public int getHour() {
        return Hour;
    }

    public void setHour(int hour) {
        Hour = hour;
    }

    public int getMinute() {
        return Minute;
    }

    public void setMinute(int minute) {
        Minute = minute;
    }

    public int getAlarmID() {
        return AlarmID;
    }

    public void setAlarmID(int alarmID) {
        AlarmID = alarmID;
    }
}
