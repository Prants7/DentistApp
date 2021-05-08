package com.cgi.dentistapp.verification.DentistVisitChecking;

import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.verification.DentistVisitChecking.exceptions.DentistVisitRegisterException;

/**
 * Interface for Dentist visit checker
 */
public interface DentistVisitChecker {

    /**
     * Checks if an DentistVisitDTO meets the server requirements
     * @param targetDTO DTO to be checked
     * @return true if data is allowed and false with exception if not
     * @throws DentistVisitRegisterException exception group that contains all different ways data can be invalid
     */
    public boolean DTOVerification(DentistVisitDTO targetDTO) throws DentistVisitRegisterException;
}
