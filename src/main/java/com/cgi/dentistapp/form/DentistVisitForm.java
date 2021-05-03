package com.cgi.dentistapp.form;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Form for adding new dentist visit or changing an old entry
 */
public class DentistVisitForm {
    @Size(min = 1, max = 50)
    String dentistName;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    Date visitDate;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    Date visitTime;

    public String getDentistName() {
        return dentistName;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }
}
