package com.example.capstone1;

import java.io.Serializable;

public class medication_history_info implements Serializable {

    String Medication, StartDate, Time;
    public medication_history_info() {}

    public medication_history_info(String medication, String startDate, String time) {
        this.Medication = medication;
        this.StartDate = startDate;
        this.Time = time;
    }

    public String getMedication() {
        return Medication;
    }

    public void setMedication(String medication) {
        Medication = medication;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
