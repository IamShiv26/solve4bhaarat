package com.example.eldercare;

public class SugarDets {
    public String type, date, time;
    public int sugar_level;

    public SugarDets(){

    }

    public int getSugar_level() {
        return sugar_level;
    }

    public String getType(){
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSugar_level(int sugar_level) {
        this.sugar_level = sugar_level;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SugarDets(String type, String date, String time, int sugar_level){
        this.type = type;
        this.date=date;
        this.time = time;
        this.sugar_level = sugar_level;
    }
}
