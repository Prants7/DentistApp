package com.cgi.dentistapp.dto;

import com.cgi.dentistapp.form.DentistVisitForm;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DTO element for dentist visit
 */
public class DentistVisitDTO {
    private Long id;
    private String dentistName;
    private Date visitDate;
    private Date visitTime;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    /**
     * empty constructor
     */
    public DentistVisitDTO() {
    }

    /**
     * constructor
     * @param dentistName dentist name
     * @param visitDate visit date
     * @param visitTime visit time
     */
    public DentistVisitDTO(String dentistName, Date visitDate, Date visitTime) {
        this.dentistName = dentistName;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
    }

    /**
     * constructor
     * @param visitForm form that contains all the data
     */
    public DentistVisitDTO(DentistVisitForm visitForm) {
        this.dentistName = visitForm.getDentistName();
        this.visitDate = visitForm.getVisitDate();
        this.visitTime = visitForm.getVisitTime();
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

    /**
     * Checks if id has been set on this DTO
     * @return
     */
    public boolean hasId() {
        return id != null;
    }

}
