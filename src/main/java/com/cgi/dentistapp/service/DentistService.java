package com.cgi.dentistapp.service;

import com.cgi.dentistapp.entity.DentistEntity;
import com.cgi.dentistapp.repositories.DentistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DentistService {

    @Autowired
    private DentistRepository dentistRepository;

    private List<DentistEntity> allDentists;

    public List<DentistEntity> getAllDentists() {
        if(this.allDentists == null) {
            this.setUpDummyData();
        }
        return this.dentistRepository.findAll();
    }

    public DentistEntity findDentistByName(String firstName, String lastName) {
        List<DentistEntity> foundDentists = this.dentistRepository.findByFirstNameAndLastName(firstName, lastName);
        if(foundDentists.isEmpty()) {
            return null;
        }
        return foundDentists.get(0);
    }

    public DentistEntity findDentistByName(String fullName) {
        String[] nameParts = fullName.split(" ");
        return findDentistByName(nameParts[0], nameParts[1]);
    }

    private void setUpDummyData() {
        this.initDummyDataList();
        this.makeDummyData();
        this.addDentistToDatabase(this.allDentists);
        //testFindAbility("Hedge", "Hog");
    }

    private void makeDummyData() {
        this.allDentists.add(new DentistEntity("Hedge", "Hog"));
        this.allDentists.add(new DentistEntity("John", "Smith"));
        this.allDentists.add(new DentistEntity("Dummy", "Dentistsdata1"));
    }

    private void addDentistToDatabase(List<DentistEntity> elementsToAdd) {
        for(DentistEntity oneDentist : elementsToAdd) {
            this.dentistRepository.save(oneDentist);
        }
    }

    private void initDummyDataList() {
        this.allDentists = new ArrayList<>();
    }

    /*private void testFindAbility(String firstName, String lastName) {
        System.out.println("Starting to printing out names");
        for(DentistEntity oneFoundEntity : this.dentistRepository.findByFirstNameAndLastName(firstName, lastName)) {
            System.out.println(oneFoundEntity.getFullName());
        }
        System.out.println("finished with printing");
    }*/
}
