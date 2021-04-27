package com.cgi.dentistapp.verification.DentistVisitChecking;

import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.verification.DentistVisitChecking.exceptions.DentistVisitRegisterException;

public interface DentistVisitChecker {

    public boolean DTOVerification(DentistVisitDTO targetDTO) throws DentistVisitRegisterException;
}
