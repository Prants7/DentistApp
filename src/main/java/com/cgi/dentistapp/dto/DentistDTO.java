package com.cgi.dentistapp.dto;

/**
 * DTO element for dentist
 */
public class DentistDTO {
    private Long id;
    private String name;

    /**
     * empty constructor
     */
    public DentistDTO() {}

    /**
     * Constructor
     * @param id id for DTO
     * @param name dentist name
     */
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

    /**
     * Checks if id has been set
     * @return
     */
    public boolean hasId() {
        return this.id != null;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Used on page template to check if this dentist has been previously selected in a DTO
     * @param target object that should be a dentistVisitDTO
     * @return true if this dentist has been selected previously, false if not
     */
    public boolean checkIfSelectedInDentistVisitDTO(Object target) {
        if(target == null) {
            return false;
        }
        if(target.getClass() != DentistVisitDTO.class) {
            return false;
        }
        DentistVisitDTO castTarget = (DentistVisitDTO) target;
        if(!this.name.equals(castTarget.getDentistName())) {
            return false;
        }
        return true;
    }
}
