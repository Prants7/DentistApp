package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dto.VisitationDateDTO;
import com.cgi.dentistapp.dto.VisitationTimeDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class AvailableDateTimeService {
    private List<VisitationTimeDTO> visitationTimes;

    public List<VisitationDateDTO> getAllVisitationDates() {
        return produceWorkdaysFromNext14Days();
    }

    private List<VisitationDateDTO> produceWorkdaysFromNext14Days() {
        List<VisitationDateDTO> listOfDays = new ArrayList<>();
        Calendar usedDay = this.produceCalendarToday();
        for(int i=0; i < 14; i++) {
            addADay(usedDay);
            if(!isWeekend(usedDay)) {
                listOfDays.add(this.getVisitationDateDTO(usedDay));
            }
        }
        return listOfDays;
    }

    private Calendar produceCalendarToday() {
        Calendar today = Calendar.getInstance();
        return today;
    }

    private VisitationDateDTO getVisitationDateDTO(Calendar targetCalendar) {
        return new VisitationDateDTO(getThisDay(targetCalendar),
                getThisMonth(targetCalendar),
                getThisYear(targetCalendar));
    }

    private void addADay(Calendar fromThisDay) {
        fromThisDay.add(Calendar.DATE, 1);
    }

    private boolean isWeekend(Calendar dayInQuestion) {
        int dayNr = dayInQuestion.get(Calendar.DAY_OF_WEEK);
        if(dayNr == 1 || dayNr == 7) {
            return true;
        }
        return false;
    }

    private int getThisDay(Calendar element) {
        return element.get(Calendar.DATE);
    }

    private int getThisMonth(Calendar element) {
        return element.get(Calendar.MONTH) +1;
    }

    private int getThisYear(Calendar element) {
        return element.get(Calendar.YEAR);
    }

    public List<VisitationTimeDTO> getAllVisitationTimes() {
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
            this.visitationTimes.add(new VisitationTimeDTO(8+i, 0));
            this.visitationTimes.add(new VisitationTimeDTO( 8+i, 30));
        }
    }

    private void initDummyTimeList() {
        this.visitationTimes = new ArrayList<>();
    }
}
