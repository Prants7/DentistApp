package com.cgi.dentistapp.entity;

import com.cgi.dentistapp.dto.DentistVisitDTO;

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
        if(!this.toString().equals(castTarget.getVisitTimeString())) {
            //System.out.println("Check was false, on name equals");
            return false;
        }
        //System.out.println("Check was true");
        return true;
    }
}
