package com.cgi.dentistapp.service;

import com.cgi.dentistapp.entity.DentistEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DentistService {
    private List<DentistEntity> allDentists;

    public List<DentistEntity> getAllDentists() {
        if(this.allDentists == null) {
            this.setUpDummyData();
        }
        return this.allDentists;
    }

    private void setUpDummyData() {
        this.initDummyDataList();
        this.makeDummyData();
    }

    private void makeDummyData() {
        this.allDentists.add(new DentistEntity("Hedge", "Hog"));
        this.allDentists.add(new DentistEntity("John", "Smith"));
        this.allDentists.add(new DentistEntity("Dummy", "Dentistsdata1"));
    }

    private void initDummyDataList() {
        this.allDentists = new ArrayList<>();
    }
}
