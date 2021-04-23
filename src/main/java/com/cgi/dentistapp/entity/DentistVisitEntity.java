package com.cgi.dentistapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "dentist_visit")
public class DentistVisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="dentist_id", nullable = false)
    private DentistEntity dentist;
    private String dateTime;

    public DentistVisitEntity() {}

    public DentistVisitEntity(DentistEntity dentist, String dateTime) {
        this.dentist = dentist;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public DentistEntity getDentist() {
        return dentist;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDentistName(DentistEntity dentist) {
        this.dentist = dentist;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String toString() {
        return this.dentist.getFullName() + " at: " + this.dateTime;
    }
}
