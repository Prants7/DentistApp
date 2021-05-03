package com.cgi.dentistapp.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DentistVisitDTO {

    Long id;

    @Size(min = 1, max = 50)
    String dentistName;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    Date visitDate;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    Date visitTime;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");


    public DentistVisitDTO() {
    }

    public DentistVisitDTO(String dentistName, Date visitDate, Date visitTime) {
        this.dentistName = dentistName;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public String getVisitDateString() {
        return this.dateFormat.format(this.visitDate);
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public String getVisitTimeString() {
        return this.timeFormat.format(this.visitTime);
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean hasId() {
        return id != null;
    }

    public boolean checkEquality(Object target) {
        System.out.println("Checking equality");
        if(target == null) {
            System.out.println("Check was false");
            return false;
        }
        if(target.getClass() != this.getClass()) {
            System.out.println("Check was false");
            return false;
        }
        if(!this.hasId()) {
            System.out.println("Check was false");
            return false;
        }
        DentistVisitDTO castTarget = (DentistVisitDTO) target;
        if(this.id != castTarget.getId()) {
            System.out.println("Check was false");
            return false;
        }
        System.out.println("Check was true");
        return true;
    }
}
