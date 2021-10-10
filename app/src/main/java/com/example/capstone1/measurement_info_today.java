package com.example.capstone1;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.Date;

public class  measurement_info_today implements Serializable{
    String HMName, Time, FrequencyTitle;
    Date StartDate, EndDate;
    int  Frequency, idCode, Hour, Minute;
    @Exclude
    private String id;



    public measurement_info_today() {}


    public measurement_info_today(String HMName, String time, Date startDate, Date endDate, int frequency, String frequencyTitle) {
        this.HMName = HMName;
        this.Time = time;
        this.StartDate = startDate;
        this.EndDate = endDate;
        this.Frequency = frequency;
        this.FrequencyTitle = frequencyTitle;
        this.id = id;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHMName() {
        return HMName;
    }

    public void setHMName(String HMName) {
        this.HMName = HMName;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public int getFrequency() {
        return Frequency;
    }

    public void setFrequency(int frequency) {
        Frequency = frequency;
    }

    public String getFrequencyTitle() {
        return FrequencyTitle;
    }

    public void setFrequencyTitle(String frequencyTitle) {
        FrequencyTitle = frequencyTitle;
    }

    public int getIdCode() {
        return idCode;
    }

    public void setIdCode(int idCode) {
        this.idCode = idCode;
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
}
