package com.cgi.dentistapp.dto;

/**
 * DTO class for visitationTime
 */
public class VisitationTimeDTO {
    private int hour;
    private int minute;

    /**
     * constructor
     * @param hour hour
     * @param minute minute
     */
    public VisitationTimeDTO(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    /**
     * toString meethod
     * @return time as string in form Hour:Minute
     */
    public String toString() {
        return this.getHourString()+":"+this.getMinuteString();
    }

    /**
     * Returns hour as a double digit string
     * @return string version of hour
     */
    private String getHourString() {
        if(hour < 10) {
            return "0"+this.hour;
        }
        return ""+this.hour;
    }

    /**
     * Returns minute as a double digit string
     * @return string version of minutes
     */
    private String getMinuteString() {
        if(minute < 10) {
            return "0"+this.minute;
        }
        return ""+this.minute;
    }

    /**
     * Checks if a selected dentistVisitDTO has this time selected
     * @param target object that should be a dentistVisitDTO to succeed
     * @return true if the DTO has this time selected, false if not
     */
    public boolean checkIfSelectedInDentistVisitDTO(Object target) {
        if(target == null) {
            return false;
        }
        if(target.getClass() != DentistVisitDTO.class) {
            return false;
        }
        DentistVisitDTO castTarget = (DentistVisitDTO) target;
        if(!this.toString().equals(castTarget.getVisitTimeString())) {
            return false;
        }
        return true;
    }
}
