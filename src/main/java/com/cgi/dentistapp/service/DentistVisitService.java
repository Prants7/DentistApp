package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import com.cgi.dentistapp.repositories.DentistVisitRepository;
import com.cgi.dentistapp.verification.DentistVisitVerification.DentistVisitChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    /*public void addVisit(String dentistName, Date visitTime) {
        if(dentistService.findDentistByName(dentistName) == null) {
            return;
        }
        this.dentistVisitRepository.save(this.makeNewDentistVisitEntity(dentistName, visitTime));
    }*/

    public void addVisit(DentistVisitDTO visitDTO) {
        DentistVisitEntity entityAttempt = this.DTOToEntity(visitDTO);
        if(entityAttempt != null) {
            this.dentistVisitRepository.save(entityAttempt);
        }
    }

    public List<DentistVisitEntity> getAllVisits() {
        return dentistVisitRepository.findAll();
    }

    private DentistVisitEntity makeNewDentistVisitEntity(String dentistName, Date visitTime) {
        DentistVisitEntity resultEntity = new DentistVisitEntity(visitTime.toString());
        resultEntity = this.dentistService.addDentistEntityToVisitEntity(resultEntity, this.dentistService.getIdOfDentistByName(dentistName));
        return resultEntity;
    }

    private DentistVisitEntity DTOToEntity(DentistVisitDTO DTOElement) {
        if(!this.allowedToTurnIntoEntity(DTOElement)) {
            return null;
        }
        DentistVisitEntity resultEntity = new DentistVisitEntity(DTOElement.getVisitDate().toString());
        resultEntity = this.dentistService.addDentistEntityToVisitEntity(resultEntity, this.dentistService.getIdOfDentistByName(DTOElement.getDentistName()));
        return resultEntity;
    }

    private boolean allowedToTurnIntoEntity(DentistVisitDTO elementToCheck) {
        return checker.DTOVerification(elementToCheck);
    }
}
