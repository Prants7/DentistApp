package com.cgi.dentistapp.verification.DentistVisitChecking;

import com.cgi.dentistapp.dto.DentistVisitDTO;
import org.springframework.stereotype.Component;

@Component("VisitCheckerImpl")
public class VisitCheckerImpl implements DentistVisitChecker {

    @Override
    public boolean DTOVerification(DentistVisitDTO targetDTO) {
        if(!hasActualDentist(targetDTO)) {
            System.out.println("failed at dentist check");
            return false;
        }
        if(!hasAllowedDate(targetDTO)) {
            System.out.println("failed at allowed date check");
            return false;
        }
        if(!hasAvailableDate(targetDTO)) {
            System.out.println("failed at available date check");
            return false;
        }
        return true;
    }

    private boolean hasActualDentist(DentistVisitDTO targetDTO) {
        //todo
        return true;
    }

    private boolean hasAllowedDate(DentistVisitDTO targetDTO) {
        //todo
        return true;
    }

    private boolean hasAvailableDate(DentistVisitDTO targetDTO) {
        //todo
        return true;
    }
}
