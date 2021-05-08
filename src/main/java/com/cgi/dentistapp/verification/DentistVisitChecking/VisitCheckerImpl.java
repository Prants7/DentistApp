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

/**
 * Standard implementation for a DentistVisitChecker
 */
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

    /**
     * Checks if DTO has a dentist that is saved in the app
     * @param targetDTO checked DTO
     * @return true if dentist is saved in repository, exception if not
     */
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

    /**
     * Makes a new standard DentistNotFoundException
     * @param problemSource DTO that triggers the exception
     * @return new exception object
     */
    private DentistNotFoundException prepareNewDentistNotFoundException(DentistVisitDTO problemSource) {
        return new DentistNotFoundException("Cant find dentist with name "+problemSource.getDentistName());
    }

    /**
     * Checks if DTO has an allowed date
     * @param targetDTO checked DTO
     * @return true if its allowed, and exception if not
     */
    private boolean hasAllowedDate(DentistVisitDTO targetDTO) {
        List<VisitationDateDTO> allowedDates = this.dateTimeService.getAllVisitationDates();
        if(!findMatchingDate(allowedDates, targetDTO.getVisitDateString())) {
            throw prepareNewDateNotAllowedException(targetDTO);
        }
        return true;
    }

    /**
     * Makes a new standard NotAllowedDentistException
     * @param problemSource DTO that triggers the exception
     * @return new exception object
     */
    private NotAllowedDateException prepareNewDateNotAllowedException(DentistVisitDTO problemSource) {
        return new NotAllowedDateException("Date: "+problemSource.getVisitDateString()+" is not allowed");
    }

    /**
     * method that tries to match a list of VisitationDateDTO dates to a string version of a date
     * @param checkedList list of visitationDateDTOs that will be checked for a match
     * @param searchedDateAsString string version of date that will be searched for, from the input list
     * @return true if a match was found, false if it wasn't
     */
    private boolean findMatchingDate(List<VisitationDateDTO> checkedList, String searchedDateAsString) {
        for(VisitationDateDTO oneDTO : checkedList) {
            if(oneDTO.toString().equals(searchedDateAsString)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an DentistVisitDTO has an allowed time field
     * @param targetDTO checked DTO
     * @return true if the time is allowed and exception if its not
     */
    private boolean hasAllowedTime(DentistVisitDTO targetDTO) {
        List<VisitationTimeDTO> allowedTimes = this.dateTimeService.getAllVisitationTimes();
        if(!findMatchingTime(allowedTimes, targetDTO.getVisitTimeString())) {
            throw prepareNewDTimeNotAllowedException(targetDTO);
        }
        return true;
    }

    /**
     * Makes a new NotAllowedTimeException
     * @param problemSource DTO that triggers the exception
     * @return a new exception object
     */
    private NotAllowedTimeException prepareNewDTimeNotAllowedException(DentistVisitDTO problemSource) {
        return new NotAllowedTimeException("Time: "+problemSource.getVisitDateString()+" is not allowed");
    }

    /**
     * For trying to find a string version of a time match in input list of VisitationTimeDTOs
     * @param checkedList List where a match is searched from
     * @param searchedTimeAsString String version of time that is attempted to match with
     * @return true if a match is found, false is not
     */
    private boolean findMatchingTime(List<VisitationTimeDTO> checkedList, String searchedTimeAsString) {
        for(VisitationTimeDTO oneDTO : checkedList) {
            if(oneDTO.toString().equals(searchedTimeAsString)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a DentistVisitDTOs selected time hasn't already been taken
     * @param targetDTO Checked DTO
     * @return true if the time is available, exception if not
     * @throws TimeTakenException exception when the time is already taken
     */
    private boolean hasAvailableDate(DentistVisitDTO targetDTO) throws TimeTakenException {
        List<DentistVisitDTO> searchForTakenTime = this.dentistVisitService.findVisitsWithSameTIme(targetDTO);
        if(!searchForTakenTime.isEmpty()) {
            /*if(searchForTakenTime.size() > 1) {
                throw prepareNewTimeTakenException(targetDTO);
            }
            if(!targetDTO.hasId()) {
                throw prepareNewTimeTakenException(targetDTO);
            }
            if(targetDTO.getId() != searchForTakenTime.get(0).getId()) {
                throw prepareNewTimeTakenException(targetDTO);
            }*/
            availableDateCheckAllowUpdateOnPreviousEntity(targetDTO, searchForTakenTime);
        }
        return true;
    }

    /**
     * Submethod for checking if the time is taken, but allows change if the only use of the same time has same id,
     * meaning its an update
     * @param targetDTO DTO that is checked
     * @param foundEntries entries that have the same date and time as checked element
     * @return true if input DTO has an allowed date, exception if its already taken and not allowed
     */
    private boolean availableDateCheckAllowUpdateOnPreviousEntity(
            DentistVisitDTO targetDTO, List<DentistVisitDTO> foundEntries) {
        if(foundEntries.size() > 1) {
            throw prepareNewTimeTakenException(targetDTO);
        }
        if(!targetDTO.hasId()) {
            throw prepareNewTimeTakenException(targetDTO);
        }
        if(targetDTO.getId() != foundEntries.get(0).getId()) {
            throw prepareNewTimeTakenException(targetDTO);
        }
        return true;
    }

    /**
     * For making a new TimeTakenException object
     * @param problemSource DTO that triggers the exception
     * @return new exception object
     */
    private TimeTakenException prepareNewTimeTakenException(DentistVisitDTO problemSource) {
        return new TimeTakenException("Selected time: "+
                problemSource.getVisitDateString() + "/"+
                problemSource.getVisitTimeString() +
                " has already been taken.");
    }
}
