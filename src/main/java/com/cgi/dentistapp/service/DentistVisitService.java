package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import com.cgi.dentistapp.repositories.DentistVisitRepository;
import com.cgi.dentistapp.verification.DentistVisitChecking.DentistVisitChecker;
import com.cgi.dentistapp.verification.DentistVisitChecking.exceptions.DentistVisitRegisterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DentistVisitService {
    @Autowired
    private DentistVisitRepository dentistVisitRepository;

    @Autowired
    private DentistService dentistService;

    @Autowired
    @Qualifier("VisitCheckerImpl")
    private DentistVisitChecker checker;

    private SimpleDateFormat mergedDateFormatter = new SimpleDateFormat("dd.MM.yyyy/HH:mm");

    /**
     * Method for saving a new visit to the repository
     * @param visitDTO DTO of the new visit
     * @throws DentistVisitRegisterException exceptions that are thrown when DTO posses invalid data
     */
    public void addVisit(DentistVisitDTO visitDTO) throws DentistVisitRegisterException {
        //try {
        DentistVisitEntity entityAttempt = this.DTOToEntity(visitDTO);
        this.dentistVisitRepository.save(entityAttempt);
        /*}
        catch (DentistVisitRegisterException exception) {
            throw exception;
        }*/
    }

    /**
     * For changing an existing data entry in the repository, doesn't work if DTO doesn't have an assigned id
     * @param visitDTO
     * @throws DentistVisitRegisterException exceptions that are thrown when DTO posses invalid data
     */
    public void updateVisit(DentistVisitDTO visitDTO) throws DentistVisitRegisterException {
        DentistVisitEntity entityAttempt;
        //try {
        entityAttempt = this.DTOToEntity(visitDTO);
        /*}
        catch (DentistVisitRegisterException exception) {
            throw exception;
        }*/
        if(!updatableEntityAcceptanceCheck(entityAttempt)) {
            return;
        }
        this.dentistVisitRepository.save(entityAttempt);
    }

    /**
     * Combines id checking of visit updating into one method
     * @param checkedEntity visit that is checked
     * @return false if the entity is not allowed as new version of an existing entry, true if it is
     */
    private boolean updatableEntityAcceptanceCheck(DentistVisitEntity checkedEntity) {
        if(checkedEntity == null) {
            return false;
        }
        if(checkedEntity.getId() == null) {
            return false;
        }
        return true;
    }

    /**
     * For getting all saved visits
     * @return list of visit DTOs
     */
    public List<DentistVisitDTO> getAllVisits() {
        return this.entityToDTOList(dentistVisitRepository.findAll());
    }

    /**
     * For turning a single DentistVisitDTO into a DentistVisitEntity
     * @param DTOElement DTO to be turned
     * @return if succesful, a Entity element version of input DTO
     * @throws DentistVisitRegisterException class of exceptions thrown when input data is not allowed
     */
    private DentistVisitEntity DTOToEntity(DentistVisitDTO DTOElement) throws DentistVisitRegisterException {
        //try {
        this.allowedToTurnIntoEntity(DTOElement);
        /*}
        catch(DentistVisitRegisterException exception) {
            throw exception;
        }*/
        DentistVisitEntity resultEntity = new DentistVisitEntity(formatMergeDTODateAndTime(DTOElement));
        resultEntity = this.dentistService.addDentistEntityToVisitEntity(
                resultEntity, this.dentistService.getIdOfDentistByName(DTOElement.getDentistName()));
        resultEntity = checkAndAddIdToEntity(DTOElement, resultEntity);
        return resultEntity;
    }

    /**
     * For making a complete date and time string for an entity, out of a DTO elements date and time
     * @param DTOElement DTO element used as the source for date and time
     * @return unified string that contains both date and time
     */
    private String formatMergeDTODateAndTime(DentistVisitDTO DTOElement) {
        return DTOElement.getVisitDateString()+"/"+DTOElement.getVisitTimeString();
    }

    private DentistVisitEntity checkAndAddIdToEntity(DentistVisitDTO DTOElement, DentistVisitEntity entityToChange) {
        if(DTOElement.hasId()) {
            entityToChange.setId(DTOElement.getId());
        }
        return entityToChange;
    }

    private List<DentistVisitDTO> entityToDTOList(List<DentistVisitEntity> elementEntityList) {
        List<DentistVisitDTO> finalList = new ArrayList<>();
        DentistVisitDTO oneDTO;
        for(DentistVisitEntity oneEntity : elementEntityList) {
            oneDTO = entityToDTO(oneEntity);
            if(oneDTO != null) {
                finalList.add(oneDTO);
            }
        }
        return finalList;
    }

    private DentistVisitDTO entityToDTO(DentistVisitEntity elementEntity) {
        DentistVisitDTO newDTO = generateNewDTOWithoutID(elementEntity);
        newDTO.setId(elementEntity.getId());
        return newDTO;
    }

    private DentistVisitDTO generateNewDTOWithoutID(DentistVisitEntity elementEntity) {
        Date newDate = attemptToExtractDateFromEntity(elementEntity);
        DentistVisitDTO newDTO = new DentistVisitDTO(elementEntity.getDentist().getName(), newDate, newDate);
        return newDTO;
    }

    private Date attemptToExtractDateFromEntity(DentistVisitEntity elementEntity) {
        //NB! if it corrupts it gives current moment
        Date newDate = new Date();
        try {
            newDate = mergedDateFormatter.parse(elementEntity.getDateTime());
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
        return newDate;
    }

    private boolean allowedToTurnIntoEntity(DentistVisitDTO elementToCheck) {
        return checker.DTOVerification(elementToCheck);
    }

    public DentistVisitDTO getVisitById(Long id) {
        DentistVisitEntity foundEntity = this.dentistVisitRepository.findOne(id);
        if(foundEntity != null) {
            return this.entityToDTO(foundEntity);
        }
        return null;
    }

    public boolean deleteEntry(Long id) {
        this.dentistVisitRepository.delete(id);
        return true;
    }

    public List<DentistVisitDTO> findVisitsWithSameTIme(DentistVisitDTO visitDTO) {
        String searchString = this.formatMergeDTODateAndTime(visitDTO);
        List<DentistVisitEntity> foundEntities = this.dentistVisitRepository.findByDateTime(searchString);
        return this.entityToDTOList(foundEntities);
    }

    public List<DentistVisitDTO> findByDentistNameContains(String firstNamePart) {
        return this.entityToDTOList(this.dentistVisitRepository.findByDentistNameContains(firstNamePart));
    }

    public List<DentistVisitDTO> findByDateTimeContains(String dateTimePart) {
        return this.entityToDTOList(this.dentistVisitRepository.findByDateTimeContains(dateTimePart));
    }
}
