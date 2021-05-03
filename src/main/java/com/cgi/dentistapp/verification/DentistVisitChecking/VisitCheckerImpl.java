package com.cgi.dentistapp.verification.DentistVisitChecking;

import com.cgi.dentistapp.dto.DentistDTO;
import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.dto.VisitationDateDTO;
import com.cgi.dentistapp.dto.VisitationTimeDTO;
import com.cgi.dentistapp.service.AvailableDateTimeService;
import com.cgi.dentistapp.service.DentistService;
import com.cgi.dentistapp.service.DentistVisitService;
import com.cgi.dentistapp.verification.DentistVisitChecking.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component("VisitCheckerImpl")
public class VisitCheckerImpl implements DentistVisitChecker {
    @Autowired
    private DentistVisitService dentistVisitService;
    @Autowired
    private DentistService dentistService;
    @Autowired
    private AvailableDateTimeService dateTimeService;

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
        if(!hasAllowedTime(targetDTO)) {
            System.out.println("failed at allowed time check");
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
        List<VisitationDateDTO> allowedDates = this.dateTimeService.getAllVisitationDates();
        if(!findMatchingDate(allowedDates, targetDTO.getVisitDateString())) {
            throw new NotAllowedDateException("Date: "+targetDTO.getVisitDateString()+" is not allowed");
        }
        return true;
    }

    private boolean findMatchingDate(List<VisitationDateDTO> checkedList, String searchedDateAsString) {
        for(VisitationDateDTO oneDTO : checkedList) {
            if(oneDTO.toString().equals(searchedDateAsString)) {
                return true;
            }
        }
        return false;
    }


    private boolean hasAllowedTime(DentistVisitDTO targetDTO) {
        List<VisitationTimeDTO> allowedTimes = this.dateTimeService.getAllVisitationTimes();
        if(!findMatchingTime(allowedTimes, targetDTO.getVisitTimeString())) {
            throw new NotAllowedTimeException("Time: "+targetDTO.getVisitTimeString()+" is not allowed");
        }
        return true;
    }

    private boolean findMatchingTime(List<VisitationTimeDTO> checkedList, String searchedTimeAsString) {
        for(VisitationTimeDTO oneDTO : checkedList) {
            if(oneDTO.toString().equals(searchedTimeAsString)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasAvailableDate(DentistVisitDTO targetDTO) throws TimeTakenException {
        //throw new TimeTakenException("Testing the exception");
        List<DentistVisitDTO> searchForTakenTime = this.dentistVisitService.findVisitsWithSameTIme(targetDTO);
        /*if(searchForTakenTime.size() > 1) {
            throw new TimeTakenException("Selected time has already been taken");
        }
        if(targetDTO.hasId()) {
            if(searchForTakenTime.get(0).getId() != targetDTO.getId()) {
                throw new TimeTakenException("Selected time has already been taken");
            }
        }*/
        if(!searchForTakenTime.isEmpty()) {
            if(searchForTakenTime.size() > 1) {
                throw new TimeTakenException("Selected time has already been taken");
            }
            if(!targetDTO.hasId()) {
                throw new TimeTakenException("Selected time has already been taken");
            }
            if(targetDTO.getId() != searchForTakenTime.get(0).getId()) {
                throw new TimeTakenException("Selected time has already been taken");
            }
        }
        return true;
    }
}
