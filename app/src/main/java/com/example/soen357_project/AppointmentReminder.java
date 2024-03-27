package com.example.soen357_project;

import java.util.Date;

public class AppointmentReminder {
    private Date date;
    private String time;
    private String dogName;
    private String description;

    // for firebase DB access
    public AppointmentReminder() {

    }

    public AppointmentReminder(Date date, String time, String dogName, String description) {
        this.date = date;
        this.time = time;
        this.dogName = dogName;
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setDogName(String dogName) {
        this.dogName = dogName;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public String getDogName() {
        return dogName;
    }
    public String getDescription() {
        return description;
    }
}
