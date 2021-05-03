package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dto.VisitationDateDTO;
import com.cgi.dentistapp.dto.VisitationTimeDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Service for getting usable dates and times
 */
@Service
public class AvailableDateTimeService {
    private List<VisitationTimeDTO> visitationTimes;

    /**
     * Getter for all allowed visitation dates
     * @return List of dates
     */
    public List<VisitationDateDTO> getAllVisitationDates() {
        return produceWorkdaysFromNext14Days();
    }

    /**
     * For making a list of work days in the next 14 days (doesn't include today)
     * @return List of specified days
     */
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

    /**
     * For getting a calendar entry for today
     * @return Calendar object
     */
    private Calendar produceCalendarToday() {
        Calendar today = Calendar.getInstance();
        return today;
    }

    /**
     * Makes a VisitationDateDTO from a Calendar object
     * @param targetCalendar target calendar object
     * @return resulting DTO
     */
    private VisitationDateDTO getVisitationDateDTO(Calendar targetCalendar) {
        return new VisitationDateDTO(getThisDay(targetCalendar),
                getThisMonth(targetCalendar),
                getThisYear(targetCalendar));
    }

    /**
     * Adds a day to the input calendar object
     * @param fromThisDay calendar object that gets advanced by a day
     */
    private void addADay(Calendar fromThisDay) {
        fromThisDay.add(Calendar.DATE, 1);
    }

    /**
     * Checkis if calendar object points at a weekend day
     * @param dayInQuestion calendar object in question
     * @return true if weekend, false if not
     */
    private boolean isWeekend(Calendar dayInQuestion) {
        int dayNr = dayInQuestion.get(Calendar.DAY_OF_WEEK);
        if(dayNr == 1 || dayNr == 7) {
            return true;
        }
        return false;
    }

    /**
     * Return int value of a calendar object day
     * @param element target
     * @return day nr, start from 1
     */
    private int getThisDay(Calendar element) {
        return element.get(Calendar.DATE);
    }

    /**
     * Returns int value of a calendar object month
     * @param element target
     * @return month nr, start from 1
     */
    private int getThisMonth(Calendar element) {
        return element.get(Calendar.MONTH) +1;
    }

    /**
     * Returns int value of a calendar object year
     * @param element target
     * @return year number
     */
    private int getThisYear(Calendar element) {
        return element.get(Calendar.YEAR);
    }

    /**
     * For getting all accepted visitation times
     * @return list of visitation times
     */
    public List<VisitationTimeDTO> getAllVisitationTimes() {
        if(this.visitationTimes == null) {
            setUpDummyTime();
        }
        return new ArrayList<>(this.visitationTimes);
    }

    /**
     * Sets up visitation time data
     */
    private void setUpDummyTime() {
        this.initDummyTimeList();
        this.makeStandardVisitationTimeSlots();
    }

    /**
     * Makes standard visitation time slot values
     */
    private void makeStandardVisitationTimeSlots() {
        //from 8 to 12 visitation times
        for(int i = 0; i < 4; i++) {
            this.visitationTimes.add(new VisitationTimeDTO(8+i, 0));
        }
        //from 13 to 15
        for(int i = 0; i < 3; i++) {
            this.visitationTimes.add(new VisitationTimeDTO(13+i, 0));
        }
    }

    /**
     * makes a memory space for saving visitation time slots
     */
    private void initDummyTimeList() {
        this.visitationTimes = new ArrayList<>();
    }
}
