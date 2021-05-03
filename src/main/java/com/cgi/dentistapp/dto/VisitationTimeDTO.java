package com.cgi.dentistapp.dto;

public class VisitationTimeDTO {

    private int hour;
    private int minute;

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
