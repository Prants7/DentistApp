package com.cgi.dentistapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "dentist_visit")
public class DentistVisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String dentistName;
    private String dateTime;

    public DentistVisitEntity() {}

    public DentistVisitEntity(String dentistName, String dateTime) {
        this.dentistName = dentistName;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public String getDentistName() {
        return dentistName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String toString() {
        return this.dentistName + " at: " + this.dateTime;
    }
}
