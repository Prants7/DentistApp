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

    //private List<DentistEntity> allDentists;

    public List<DentistDTO> getAllDentists() {
        return entityToDtoList(this.getAllDentistsEntities());
    }

    private List<DentistEntity> getAllDentistsEntities() {
        /*if(this.allDentists == null) {
            this.setUpDummyData();
        }*/
        return this.dentistRepository.findAll();
    }

    public DentistDTO findDentistByName(String firstName, String lastName) {
        return this.entityToDTO(findDentistByNameEntity(firstName, lastName));
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

    /*private void setUpDummyData() {
        this.initDummyDataList();
        this.makeDummyData();
        this.addDentistToDatabase(this.allDentists);
        //testFindAbility("Hedge", "Hog");
    }*/

    /*private void makeDummyData() {
        this.allDentists.add(new DentistEntity("Hedge", "Hog"));
        this.allDentists.add(new DentistEntity("John", "Smith"));
        this.allDentists.add(new DentistEntity("Dummy", "Dentistsdata1"));
    }*/

    /*private void addDentistToDatabase(List<DentistEntity> elementsToAdd) {
        for(DentistEntity oneDentist : elementsToAdd) {
            this.dentistRepository.save(oneDentist);
        }
    }*/

    public void addDentistToDataBase(DentistDTO newDentist) {
        this.dentistRepository.save(this.DTOToEntity(newDentist));
    }


    /*private void initDummyDataList() {
        this.allDentists = new ArrayList<>();
    }*/

    /*private void testFindAbility(String firstName, String lastName) {
        System.out.println("Starting to printing out names");
        for(DentistEntity oneFoundEntity : this.dentistRepository.findByFirstNameAndLastName(firstName, lastName)) {
            System.out.println(oneFoundEntity.getFullName());
        }
        System.out.println("finished with printing");
    }*/

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
