package com.example.eldercare;

public class Medicine {
    public String medicine, duration;
    public Boolean morning,afternoon,night;

    public Medicine(){

    }

    public Medicine(String medicine, String duration, Boolean morning, Boolean afternoon, Boolean night){
        this.medicine = medicine;
        this.duration=duration;
        this.morning = morning;
        this.afternoon = afternoon;
        this.night = night;
    }
}
