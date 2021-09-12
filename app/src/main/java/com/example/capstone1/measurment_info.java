package com.example.capstone1;

import java.io.Serializable;

public class measurment_info implements Serializable {
    String Record, Time, Name;
    public measurment_info() {}

    public measurment_info(String record, String time, String name) {
        this.Record = record;
        this.Time = time;
        this.Name = name;
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
}
