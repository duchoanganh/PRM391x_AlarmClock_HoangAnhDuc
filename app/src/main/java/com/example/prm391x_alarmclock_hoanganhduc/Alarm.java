package com.example.prm391x_alarmclock_hoanganhduc;

import java.io.Serializable;

public class Alarm implements Serializable {
    private int alarmId;
    private String subject;
    private int hour;
    private int minute;
    private boolean isActive;
    public Alarm(){

    }

    public Alarm(int alarmId, String subject, int hour, int minute, boolean isActive) {
        this.alarmId = alarmId;
        this.subject = subject;
        this.hour = hour;
        this.minute = minute;
        this.isActive = isActive;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
