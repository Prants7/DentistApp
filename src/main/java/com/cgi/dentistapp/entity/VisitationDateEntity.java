package com.cgi.dentistapp.entity;


import com.cgi.dentistapp.dto.DentistVisitDTO;

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

    public boolean checkIfSelectedInDentistVisitDTO(Object target) {
        //System.out.println("Checking equality for dentist");
        if(target == null) {
            //System.out.println("Check was false, on null");
            return false;
        }
        if(target.getClass() != DentistVisitDTO.class) {
            //System.out.println("Check was false, on dentistVisitDTO class");
            return false;
        }
        DentistVisitDTO castTarget = (DentistVisitDTO) target;
        if(!this.toString().equals(castTarget.getVisitDateString())) {
            //System.out.println("Check was false, on name equals");
            return false;
        }
        //System.out.println("Check was true");
        return true;
    }

}
