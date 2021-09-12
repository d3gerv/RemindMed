package com.example.capstone1;

public class types_of_measurements {
    String Bloodpressure, Bloodsugar, Cholesterol, Heartrate, Pulserate, Sleep, Temperature;

    public types_of_measurements( ){}

    public types_of_measurements(String bloodpressure, String bloodsugar, String cholesterol, String heartrate, String pulserate, String sleep, String temperature) {
        this.Bloodpressure = bloodpressure;
        this.Bloodsugar = bloodsugar;
        this.Cholesterol = cholesterol;
        this.Heartrate = heartrate;
        this.Pulserate = pulserate;
        this.Sleep = sleep;
        this.Temperature = temperature;
    }

    public String getBloodpressure() {
        return Bloodpressure;
    }

    public void setBloodpressure(String bloodpressure) {
        Bloodpressure = bloodpressure;
    }

    public String getBloodsugar() {
        return Bloodsugar;
    }

    public void setBloodsugar(String bloodsugar) {
        Bloodsugar = bloodsugar;
    }

    public String getCholesterol() {
        return Cholesterol;
    }

    public void setCholesterol(String cholesterol) {
        Cholesterol = cholesterol;
    }

    public String getHeartrate() {
        return Heartrate;
    }

    public void setHeartrate(String heartrate) {
        Heartrate = heartrate;
    }

    public String getPulserate() {
        return Pulserate;
    }

    public void setPulserate(String pulserate) {
        Pulserate = pulserate;
    }

    public String getSleep() {
        return Sleep;
    }

    public void setSleep(String sleep) {
        Sleep = sleep;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }
}
