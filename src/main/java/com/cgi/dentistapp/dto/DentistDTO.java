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
        if(!this.name.equals(castTarget.getDentistName())) {
            //System.out.println("Check was false, on name equals");
            return false;
        }
        //System.out.println("Check was true");
        return true;
    }
}
