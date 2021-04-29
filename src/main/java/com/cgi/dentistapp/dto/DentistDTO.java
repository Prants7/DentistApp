package com.cgi.dentistapp.dto;

public class DentistDTO {

    private Long id;
    private String name;

    public DentistDTO() {}

    public DentistDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean hasId() {
        return this.id != null;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
