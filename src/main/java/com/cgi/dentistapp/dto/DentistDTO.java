package com.cgi.dentistapp.dto;

public class DentistDTO {

    private Long id;
    private String firstName;
    private String lastName;

    public DentistDTO() {}

    public DentistDTO(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean hasId() {
        return this.id != null;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
