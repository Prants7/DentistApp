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

    public void addVisit(DentistVisitDTO visitDTO) throws DentistVisitRegisterException {
        try {
            DentistVisitEntity entityAttempt = this.DTOToEntity(visitDTO);
            this.dentistVisitRepository.save(entityAttempt);
        }
        catch (DentistVisitRegisterException exception) {
            throw exception;
        }
        /*DentistVisitEntity entityAttempt = this.DTOToEntity(visitDTO);
        if(entityAttempt != null) {
            this.dentistVisitRepository.save(entityAttempt);
        }*/
    }

    public void updateVisit(DentistVisitDTO visitDTO) {
        DentistVisitEntity entityAttempt = this.DTOToEntity(visitDTO);
        if(entityAttempt == null) {
            return;
        }
        if(entityAttempt.getId() == null) {
            return;
        }
        this.dentistVisitRepository.save(entityAttempt);
    }

    public List<DentistVisitDTO> getAllVisits() {
        return this.entityToDTOList(dentistVisitRepository.findAll());
    }

    private DentistVisitEntity DTOToEntity(DentistVisitDTO DTOElement) throws DentistVisitRegisterException {
        try {
            this.allowedToTurnIntoEntity(DTOElement);
        }
        catch(DentistVisitRegisterException exception) {
            throw exception;
        }
        /*if(!this.allowedToTurnIntoEntity(DTOElement)) {
            return null;
        }*/
        DentistVisitEntity resultEntity = new DentistVisitEntity(formatMergeDTODateAndTime(DTOElement));
        resultEntity = this.dentistService.addDentistEntityToVisitEntity(
                resultEntity, this.dentistService.getIdOfDentistByName(DTOElement.getDentistName()));
        resultEntity = checkAndAddIdToEntity(DTOElement, resultEntity);
        return resultEntity;
    }

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

    private void debugTestTimeSearch(DentistVisitDTO testDTO) {
        System.out.println("performing search for same time previous entries");
        for(DentistVisitDTO oneFoundDTO : this.findVisitsWithSameTIme(testDTO)) {
            System.out.println("found for: "+oneFoundDTO.getDentistName()+ " on "+oneFoundDTO.getVisitTimeString()+" / "+oneFoundDTO.getVisitDateString());
        }
        System.out.println("search over");
    }

    public List<DentistVisitDTO> findVisitsWithSameTIme(DentistVisitDTO visitDTO) {
        String searchString = this.formatMergeDTODateAndTime(visitDTO);
        List<DentistVisitEntity> foundEntities = this.dentistVisitRepository.findByDateTime(searchString);
        return this.entityToDTOList(foundEntities);
    }

    public List<DentistVisitDTO> findVisitsForDentist(Long dentistId) {
        return this.entityToDTOList(this.dentistVisitRepository.findByDentist_id(dentistId));
    }

    public List<DentistVisitDTO> findByDentistFirstNameContains(String firstNamePart) {
        return this.entityToDTOList(this.dentistVisitRepository.findByDentistNameContains(firstNamePart));
    }

    public List<DentistVisitDTO> findByDateTimeContains(String dateTimePart) {
        return this.entityToDTOList(this.dentistVisitRepository.findByDateTimeContains(dateTimePart));
    }
}
