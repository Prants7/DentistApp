package com.cgi.dentistapp.verification.DentistVisitVerification;

import com.cgi.dentistapp.dto.DentistVisitDTO;
import org.springframework.stereotype.Component;

@Component("VisitCheckerImpl")
public class VisitCheckerImpl implements DentistVisitChecker {

    @Override
    public boolean DTOVerification(DentistVisitDTO targetDTO) {
        return true;
    }
}
