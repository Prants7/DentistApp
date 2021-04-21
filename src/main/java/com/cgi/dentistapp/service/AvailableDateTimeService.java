package com.cgi.dentistapp.service;

import com.cgi.dentistapp.entity.DentistEntity;
import com.cgi.dentistapp.entity.VisitationDateEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AvailableDateTimeService {
    private List<VisitationDateEntity> visitationDates;

    public List<VisitationDateEntity> getAllVisitationDates() {
        if(this.visitationDates == null) {
            this.setUpDummyData();
        }
        return this.visitationDates;
    }

    private void setUpDummyData() {
        this.initDummyDataList();
        this.makeDummyData();
    }

    private void makeDummyData() {
        this.visitationDates.add(new VisitationDateEntity(24,4, 2021));
        this.visitationDates.add(new VisitationDateEntity(25,4, 2021));
        this.visitationDates.add(new VisitationDateEntity(3,5, 2021));
        this.visitationDates.add(new VisitationDateEntity(4,5, 2021));
    }

    private void initDummyDataList() {
        this.visitationDates = new ArrayList<>();
    }
}
