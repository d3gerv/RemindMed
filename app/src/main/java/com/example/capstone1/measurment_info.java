package com.example.capstone1;

import java.io.Serializable;

public class measurment_info implements Serializable {
    String Record, Time, Name, Date;
    public measurment_info() {}



    public measurment_info(String record, String time, String name, String date) {
        this.Record = record;
        this.Time = time;
        this.Name = name;
        this.Date = date;
    }

    public String getRecord() {
        return Record;
    }

    public void setRecord(String record) {
        Record = record;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
