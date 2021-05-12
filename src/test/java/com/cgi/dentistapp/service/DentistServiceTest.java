package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dto.DentistDTO;
import com.cgi.dentistapp.entity.DentistEntity;
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
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DentistServiceTest extends TestCase {

    @MockBean
    DentistRepository mockRepository;

    @Autowired
    DentistService dentistService;

    @Before
    public void setUpMockRepository() {
        List<DentistEntity> testableList = this.makeListOfDentistEntities(this.getTestableNames());
        Mockito.when(mockRepository.findAll()).thenReturn(testableList);
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
        for(String oneName : dentistNames) {
            DentistEntity newEntry = new DentistEntity();
            newEntry.setName(oneName);
            dentists.add(newEntry);
        }
        return dentists;
    }



    @Test
    public void serviceCanReturnAListOfDentistDTOs() {
        List<DentistDTO> listOfDentists = dentistService.getAllDentists();
        assertEquals("Returned list needs to have 4 elements", 4, listOfDentists.size());
        List<String> names = this.getTestableNames();
        assertEquals("Element names need to stay same for DTO", names.get(0), listOfDentists.get(0).getName());
        assertEquals("Service must handle different names for DTO", names.get(1), listOfDentists.get(1).getName());
    }

}