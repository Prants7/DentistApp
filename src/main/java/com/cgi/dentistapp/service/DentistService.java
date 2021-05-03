package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dto.DentistDTO;
import com.cgi.dentistapp.entity.DentistEntity;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import com.cgi.dentistapp.repositories.DentistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for dealing with dentists
 */
@Service
public class DentistService {
    @Autowired
    private DentistRepository dentistRepository;

    /**
     * getter for all dentists
     * @return list of all dentists
     */
    public List<DentistDTO> getAllDentists() {
        return entityToDtoList(this.getAllDentistsEntities());
    }

    /**
     * private method for fetching dentists from repository
     * @return list of dentists entities
     */
    private List<DentistEntity> getAllDentistsEntities() {
        return this.dentistRepository.findAll();
    }

    /**
     * For getting the id of a dentist by name
     * @param fullName full name of the dentist
     * @return null if dentist is not in repository, if of the dentist if they are
     */
    public Long getIdOfDentistByName(String fullName) {
        DentistDTO foundDentist = this.findDentistByName(fullName);
        if(foundDentist == null) {
            return null;
        }
        return foundDentist.getId();
    }

    /**
     * For getting entity of a dentist by name
     * @param name name of the dentist
     * @return null if not found, otherwise a dentist entity
     */
    private DentistEntity findDentistByNameEntity(String name) {
        List<DentistEntity> foundDentists = this.dentistRepository.findByName(name);
        if(foundDentists.isEmpty()) {
            return null;
        }
        return foundDentists.get(0);
    }

    /**
     * For adding a dentist entity to a dentist visit entity for future storage
     * @param entityToChange Dentist visit entity that will be changed
     * @param dentistId long id of the dentist entity that should be added
     * @return entity that was changed
     */
    public DentistVisitEntity addDentistEntityToVisitEntity(DentistVisitEntity entityToChange, Long dentistId) {
        DentistEntity foundEntity = this.dentistRepository.findOne(dentistId);
        if(foundEntity != null) {
            entityToChange.setDentistEntity(foundEntity);
        }
        return entityToChange;
    }

    /**
     * for finding a dentist by name
     * @param name name that is searched for
     * @return null if not found, DTO of the dentist if they were
     */
    public DentistDTO findDentistByName(String name) {
        DentistEntity foundDentist = findDentistByNameEntity(name);
        if(foundDentist != null) {
            return this.entityToDTO(foundDentist);
        }
        return null;
    }

    /**
     * for adding a new dentist through code
     * @param newDentist new dentist values
     */
    public void addDentistToDataBase(DentistDTO newDentist) {
        this.dentistRepository.save(this.DTOToEntity(newDentist));
    }

    /**
     * Method that turns a list of entities to a list of DTOs
     * @param listOfElements list of entities to be turned into DTOs
     * @return List of DTOs
     */
    private List<DentistDTO> entityToDtoList(List<DentistEntity> listOfElements) {
        List<DentistDTO> finalList = new ArrayList<>();
        for(DentistEntity oneEntity : listOfElements) {
            finalList.add(entityToDTO(oneEntity));
        }
        return finalList;
    }

    /**
     * method for tunring one dentist entity to a DTO
     * @param element entity element to be converted
     * @return DTO based on input
     */
    private DentistDTO entityToDTO(DentistEntity element) {
        return new DentistDTO(element.getId(), element.getName());
    }

    /**
     * Method for turning a dentist DTO into an entity
     * @param elementDTO element DTO to be converted
     * @return new Entity based on the DTO
     */
    private DentistEntity DTOToEntity(DentistDTO elementDTO) {
        DentistEntity finalEntity = new DentistEntity(elementDTO.getName());
        if(elementDTO.hasId()) {
            finalEntity.setId(elementDTO.getId());
        }
        return new DentistEntity();
    }
}
