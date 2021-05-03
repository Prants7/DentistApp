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
            return false;
        }
        if(!hasAllowedDate(targetDTO)) {
            return false;
        }
        if(!hasAllowedTime(targetDTO)) {
            return false;
        }
        if(!hasAvailableDate(targetDTO)) {
            return false;
        }
        return true;
    }

    private boolean hasActualDentist(DentistVisitDTO targetDTO) {
        DentistDTO attemptToFindDentist = this.dentistService.findDentistByName(targetDTO.getDentistName());
        if(attemptToFindDentist == null) {
            throw prepareNewDentistNotFoundException(targetDTO);
        }
        if(!attemptToFindDentist.getName().equals(targetDTO.getDentistName())) {
            throw prepareNewDentistNotFoundException(targetDTO);
        }
        return true;
    }

    private DentistNotFoundException prepareNewDentistNotFoundException(DentistVisitDTO problemSource) {
        return new DentistNotFoundException("Cant find dentist with name "+problemSource.getDentistName());
    }

    private boolean hasAllowedDate(DentistVisitDTO targetDTO) {
        List<VisitationDateDTO> allowedDates = this.dateTimeService.getAllVisitationDates();
        if(!findMatchingDate(allowedDates, targetDTO.getVisitDateString())) {
            throw prepareNewDateNotAllowedException(targetDTO);
        }
        return true;
    }

    private NotAllowedDateException prepareNewDateNotAllowedException(DentistVisitDTO problemSource) {
        return new NotAllowedDateException("Date: "+problemSource.getVisitDateString()+" is not allowed");
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
            throw prepareNewDTimeNotAllowedException(targetDTO);
        }
        return true;
    }

    private NotAllowedTimeException prepareNewDTimeNotAllowedException(DentistVisitDTO problemSource) {
        return new NotAllowedTimeException("Time: "+problemSource.getVisitDateString()+" is not allowed");
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
        List<DentistVisitDTO> searchForTakenTime = this.dentistVisitService.findVisitsWithSameTIme(targetDTO);
        if(!searchForTakenTime.isEmpty()) {
            if(searchForTakenTime.size() > 1) {
                throw prepareNewTimeTakenException(targetDTO);
            }
            if(!targetDTO.hasId()) {
                throw prepareNewTimeTakenException(targetDTO);
            }
            if(targetDTO.getId() != searchForTakenTime.get(0).getId()) {
                throw prepareNewTimeTakenException(targetDTO);
            }
        }
        return true;
    }

    private TimeTakenException prepareNewTimeTakenException(DentistVisitDTO problemSource) {
        return new TimeTakenException("Selected time: "+
                problemSource.getVisitDateString() + "/"+
                problemSource.getVisitTimeString() +
                " has already been taken.");
    }
}
