package com.cgi.dentistapp.entity;

import java.util.ArrayList;
import java.util.List;

public class VisitationDateEntity {

    private int day;
    private int month;
    private int year;
    private List<VisitationTimeEntity> usableTimes;

    public VisitationDateEntity(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.usableTimes = new ArrayList<>();
        generateDummyTimes();
    }

    public String toString() {
        return this.getDayString()+"."+this.getMonthString()+"."+this.getYearString();
    }

    private String getDayString() {
        if(this.day < 10) {
            return "0"+this.day;
        }
        return ""+this.day;
    }

    private String getMonthString() {
        if(this.month < 10) {
            return "0"+this.month;
        }
        return ""+this.month;
    }

    private String getYearString() {
        return ""+this.year;
    }

    private void generateDummyTimes() {
        this.usableTimes.add(new VisitationTimeEntity(10, 0));
        this.usableTimes.add(new VisitationTimeEntity(10, 30));
        this.usableTimes.add(new VisitationTimeEntity(11, 0));
        this.usableTimes.add(new VisitationTimeEntity(11, 30));
        this.usableTimes.add(new VisitationTimeEntity(14, 0));
        this.usableTimes.add(new VisitationTimeEntity(15, 0));
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public List<VisitationTimeEntity> getUsableTimes() {
        return usableTimes;
    }
}
