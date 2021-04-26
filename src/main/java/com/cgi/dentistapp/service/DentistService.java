package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dto.DentistDTO;
import com.cgi.dentistapp.entity.DentistEntity;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import com.cgi.dentistapp.repositories.DentistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DentistService {
    @Autowired
    private DentistRepository dentistRepository;

    public List<DentistDTO> getAllDentists() {
        return entityToDtoList(this.getAllDentistsEntities());
    }

    private List<DentistEntity> getAllDentistsEntities() {
        return this.dentistRepository.findAll();
    }

    public String getNameOfDentist(Long id) {
        return this.entityToDTO(this.dentistRepository.findOne(id)).getFullName();
    }

    public Long getIdOfDentistByName(String fullName) {
        DentistDTO foundDentist = this.findDentistByName(fullName);
        if(foundDentist == null) {
            return null;
        }
        return foundDentist.getId();
    }

    private DentistEntity findDentistByNameEntity(String firstName, String lastName) {
        List<DentistEntity> foundDentists = this.dentistRepository.findByFirstNameAndLastName(firstName, lastName);
        if(foundDentists.isEmpty()) {
            return null;
        }
        return foundDentists.get(0);
    }

    public DentistVisitEntity addDentistEntityToVisitEntity(DentistVisitEntity entityToChange, Long dentistId) {
        DentistEntity foundEntity = this.dentistRepository.findOne(dentistId);
        if(foundEntity != null) {
            entityToChange.setDentistEntity(foundEntity);
        }
        return entityToChange;
    }

    public DentistDTO findDentistByName(String fullName) {
        String[] nameParts = fullName.split(" ");
        return findDentistByName(nameParts[0], nameParts[1]);
    }

    public DentistDTO findDentistByName(String firstName, String lastName) {
        return this.entityToDTO(findDentistByNameEntity(firstName, lastName));
    }

    public void addDentistToDataBase(DentistDTO newDentist) {
        this.dentistRepository.save(this.DTOToEntity(newDentist));
    }

    private List<DentistDTO> entityToDtoList(List<DentistEntity> listOfElements) {
        List<DentistDTO> finalList = new ArrayList<>();
        for(DentistEntity oneEntity : listOfElements) {
            finalList.add(entityToDTO(oneEntity));
        }
        return finalList;
    }

    private DentistDTO entityToDTO(DentistEntity element) {
        return new DentistDTO(element.getId(), element.getFirstName(), element.getLastName());
    }

    private DentistEntity DTOToEntity(DentistDTO elementDTO) {
        DentistEntity finalEntity = new DentistEntity(elementDTO.getFirstName(), elementDTO.getLastName());
        if(elementDTO.hasId()) {
            finalEntity.setId(elementDTO.getId());
        }
        return new DentistEntity();
    }
}
