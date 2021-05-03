package com.cgi.dentistapp.dto;

/**
 * DTO element for VisitationDate
 */
public class VisitationDateDTO {
    private int day;
    private int month;
    private int year;

    /**
     * main constructor
     * @param day day, start from 1
     * @param month month, start from 1
     * @param year year, start from 1
     */
    public VisitationDateDTO(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * toString method
     * @return returns string in form of day.month.year
     */
    public String toString() {
        return this.getDayString()+"."+this.getMonthString()+"."+this.getYearString();
    }

    /**
     * returns a day string in double digits, adding a 0 to the front if needed
     * @return day as string of two numbers
     */
    private String getDayString() {
        if(this.day < 10) {
            return "0"+this.day;
        }
        return ""+this.day;
    }

    /**
     * returns month string in double digits adding a 0 to the front if needed
     * @return month string as two numbers
     */
    private String getMonthString() {
        if(this.month < 10) {
            return "0"+this.month;
        }
        return ""+this.month;
    }

    /**
     * Returns year as a string
     * @return year as a string
     */
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

    /**
     * Checks if a selected dentistVisitDTO has this date selected
     * @param target object that should be a dentistVisitDTO to succeed
     * @return true if the DTO has this date selected, false if not
     */
    public boolean checkIfSelectedInDentistVisitDTO(Object target) {
        if(target == null) {
            return false;
        }
        if(target.getClass() != DentistVisitDTO.class) {
            return false;
        }
        DentistVisitDTO castTarget = (DentistVisitDTO) target;
        if(!this.toString().equals(castTarget.getVisitDateString())) {
            return false;
        }
        return true;
    }

}
