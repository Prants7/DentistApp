package com.cgi.dentistapp.entity;


public class VisitationDateEntity {

    private int day;
    private int month;
    private int year;

    public VisitationDateEntity(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
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


    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

}
