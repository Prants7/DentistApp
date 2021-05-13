package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dto.DentistDTO;
import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.entity.DentistEntity;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import com.cgi.dentistapp.repositories.DentistRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntToLongFunction;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DentistServiceTest extends TestCase {

    @MockBean
    DentistRepository mockRepository;

    @Autowired
    DentistService dentistService;

    @Before
    public void setUpMockRepository() {
        List<String> testableNames = this.getTestableNames();
        List<DentistEntity> testableList = this.makeListOfDentistEntities(testableNames);
        Mockito.when(mockRepository.findAll()).thenReturn(testableList);
        Mockito.when(mockRepository.findByName(testableNames.get(0))).thenReturn(Arrays.asList(testableList.get(0)));
        Mockito.when(mockRepository.findOne(new Long(1))).thenReturn(testableList.get(0));
    }

    private DentistDTO getTestableDentistDTO(String name) {
        DentistDTO newDTO = new DentistDTO();
        newDTO.setName(name);
        return newDTO;
    }

    private List<String> getTestableNames() {
        List<String> newList = new ArrayList<>();
        newList.add("Bill Forest");
        newList.add("Mr test");
        newList.add("Im Real");
        newList.add("Leroy Jenkins");
        return newList;
    }

    private List<DentistEntity> makeListOfDentistEntities(List<String> dentistNames) {
        List<DentistEntity> dentists = new ArrayList<>();
        Long idAssigner = new Long(0);
        for(String oneName : dentistNames) {
            DentistEntity newEntry = new DentistEntity();
            newEntry.setName(oneName);
            idAssigner++;
            newEntry.setId(idAssigner);
            dentists.add(newEntry);
        }
        return dentists;
    }

    private DentistVisitEntity getTestableDentistVisitEntity() {
        DentistVisitEntity testVisit = new DentistVisitEntity();
        return testVisit;
    }

    @Test
    public void serviceCanPerformGetAll() {
        List<DentistDTO> listOfDentists = dentistService.getAllDentists();
        assertEquals("Returned list needs to have 4 elements", 4, listOfDentists.size());
        List<String> names = this.getTestableNames();
        assertEquals("Element names need to stay same for DTO", names.get(0), listOfDentists.get(0).getName());
        assertEquals("Service must handle different names for DTO", names.get(1), listOfDentists.get(1).getName());
    }

    @Test
    public void serviceCanFindIdOfDentistByName() {
        Long foundId = this.dentistService.getIdOfDentistByName(this.getTestableNames().get(0));
        assertEquals("Returns an id for first element", 1
                , (long) foundId );
    }

    @Test
    public void serviceCanAddDentistEntityToDentistVisitEntity() {
        DentistVisitEntity testVisit = this.getTestableDentistVisitEntity();
        this.dentistService.addDentistEntityToVisitEntity(testVisit, new Long(1));
        assertEquals("Visit entity should have gotten dentist with the input Id",
                new Long(1), testVisit.getDentist().getId());
    }

    @Test
    public void serviceCanFindDentistByName() {
        String name = this.getTestableNames().get(0);
        DentistDTO foundDentist = this.dentistService.findDentistByName(name);
        assertEquals("Found dentist needs to have right name", name, foundDentist.getName());
    }

    public void serviceCanAddNewDentistToRepository() {
        //TODO
    }



}