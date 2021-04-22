package com.cgi.dentistapp.service;

import com.cgi.dentistapp.entity.VisitationDateEntity;
import com.cgi.dentistapp.entity.VisitationTimeEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AvailableDateTimeService {
    private List<VisitationDateEntity> visitationDates;
    private List<VisitationTimeEntity> visitationTimes;

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

    public List<VisitationTimeEntity> getAllVisitationTimes() {
        if(this.visitationTimes == null) {
            setUpDummyTime();
        }
        return this.visitationTimes;
    }

    private void setUpDummyTime() {
        this.initDummyTimeList();
        this.makeDummyDataForTime();
    }

    private void makeDummyDataForTime() {
        for(int i = 0; i < 5; i++) {
            this.visitationTimes.add(new VisitationTimeEntity(8+i, 0));
            this.visitationTimes.add(new VisitationTimeEntity( 8+i, 30));
        }
    }

    private void initDummyTimeList() {
        this.visitationTimes = new ArrayList<>();
    }
}
