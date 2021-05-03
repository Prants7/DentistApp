package com.cgi.dentistapp.entity;

import javax.persistence.*;

/**
 * Entity element for saving dentists
 */
@Entity
@Table(name = "dentist")
public class DentistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public DentistEntity() {}

    public DentistEntity(String firstName, String lastName) {
        this.name = firstName + " " + lastName;
    }

    public DentistEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
