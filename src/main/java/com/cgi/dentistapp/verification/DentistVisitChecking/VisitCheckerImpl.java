package com.cgi.dentistapp.verification.DentistVisitChecking;

import com.cgi.dentistapp.dto.DentistDTO;
import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.service.DentistService;
import com.cgi.dentistapp.service.DentistVisitService;
import com.cgi.dentistapp.verification.DentistVisitChecking.exceptions.DentistNotFoundException;
import com.cgi.dentistapp.verification.DentistVisitChecking.exceptions.DentistVisitRegisterException;
import com.cgi.dentistapp.verification.DentistVisitChecking.exceptions.TimeTakenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("VisitCheckerImpl")
public class VisitCheckerImpl implements DentistVisitChecker {
    @Autowired
    private DentistVisitService dentistVisitService;
    @Autowired
    private DentistService dentistService;

    @Override
    public boolean DTOVerification(DentistVisitDTO targetDTO) throws DentistVisitRegisterException {
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
        DentistDTO attemptToFindDentist = this.dentistService.findDentistByName(targetDTO.getDentistName());
        if(attemptToFindDentist == null) {
            throw new DentistNotFoundException("Cant find dentist with name "+targetDTO.getDentistName());
        }
        if(!attemptToFindDentist.getName().equals(targetDTO.getDentistName())) {
            throw new DentistNotFoundException("Cant find dentist with name "+targetDTO.getDentistName());
        }
        return true;
    }

    private boolean hasAllowedDate(DentistVisitDTO targetDTO) {
        //todo
        return true;
    }

    private boolean hasAvailableDate(DentistVisitDTO targetDTO) throws TimeTakenException {
        //throw new TimeTakenException("Testing the exception");
        List<DentistVisitDTO> searchForTakenTime = this.dentistVisitService.findVisitsWithSameTIme(targetDTO);
        if(!searchForTakenTime.isEmpty()) {
            throw new TimeTakenException("Selected time has already been taken");
        }
        return true;
    }
}
