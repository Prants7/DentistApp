package com.cgi.dentistapp.service;

import com.cgi.dentistapp.entity.DentistVisitEntity;
import com.cgi.dentistapp.repositories.DentistVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DentistVisitService {

    @Autowired
    private DentistVisitRepository dentistVisitRepository;

    public void addVisit(String dentistName, Date visitTime) {
        this.dentistVisitRepository.save(this.makeNewDentistVisitEntity(dentistName, visitTime));
    }

    public List<DentistVisitEntity> getAllVisits() {
        return dentistVisitRepository.findAll();
    }

    private DentistVisitEntity makeNewDentistVisitEntity(String dentistName, Date visitTime) {
        return new DentistVisitEntity(dentistName, visitTime.toString());
    }
}
