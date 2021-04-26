package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import com.cgi.dentistapp.repositories.DentistVisitRepository;
import com.cgi.dentistapp.verification.DentistVisitChecking.DentistVisitChecker;
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

    public void addVisit(DentistVisitDTO visitDTO) {
        DentistVisitEntity entityAttempt = this.DTOToEntity(visitDTO);
        if(entityAttempt != null) {
            this.dentistVisitRepository.save(entityAttempt);
        }
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

    private DentistVisitEntity DTOToEntity(DentistVisitDTO DTOElement) {
        if(!this.allowedToTurnIntoEntity(DTOElement)) {
            return null;
        }
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
        DentistVisitDTO newDTO = new DentistVisitDTO(elementEntity.getDentist().getFullName(), newDate, newDate);
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
}
