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
        return this.entityToDTO(this.dentistRepository.findOne(id)).getName();
    }

    public Long getIdOfDentistByName(String fullName) {
        DentistDTO foundDentist = this.findDentistByName(fullName);
        if(foundDentist == null) {
            return null;
        }
        return foundDentist.getId();
    }

    private DentistEntity findDentistByNameEntity(String name) {
        List<DentistEntity> foundDentists = this.dentistRepository.findByName(name);
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

    public DentistDTO findDentistByName(String name) {
        return this.entityToDTO(findDentistByNameEntity(name));
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
        return new DentistDTO(element.getId(), element.getName());
    }

    private DentistEntity DTOToEntity(DentistDTO elementDTO) {
        DentistEntity finalEntity = new DentistEntity(elementDTO.getName());
        if(elementDTO.hasId()) {
            finalEntity.setId(elementDTO.getId());
        }
        return new DentistEntity();
    }
}
