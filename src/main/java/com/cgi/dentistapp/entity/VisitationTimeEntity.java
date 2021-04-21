package com.cgi.dentistapp.entity;

public class VisitationTimeEntity {

    private int hour;
    private int minute;

    public VisitationTimeEntity(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String toString() {
        return this.getHourString()+":"+this.getMinuteString();
    }

    private String getHourString() {
        if(hour < 10) {
            return "0"+this.hour;
        }
        return ""+this.hour;
    }

    private String getMinuteString() {
        if(minute < 10) {
            return "0"+this.minute;
        }
        return ""+this.minute;
    }
}
