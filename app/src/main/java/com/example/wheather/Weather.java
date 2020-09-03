package com.example.wheather;

public class Weather {
    private String Day;
    private String State;
    private String Image;
    private String MaxTemp;
    private String MinTemp;

    public Weather(String day, String state, String image, String maxTemp, String minTemp) {
        Day = day;
        State = state;
        Image = image;
        MaxTemp = maxTemp;
        MinTemp = minTemp;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMaxTemp() {
        return MaxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        MaxTemp = maxTemp;
    }

    public String getMinTemp() {
        return MinTemp;
    }

    public void setMinTemp(String minTemp) {
        MinTemp = minTemp;
    }




}
