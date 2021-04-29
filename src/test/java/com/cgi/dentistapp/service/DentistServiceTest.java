package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dto.DentistDTO;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DentistServiceTest extends TestCase {

    @Autowired
    private DentistService dentistService;

    /*private DentistDTO getTestableDentistDTO(String firstName, String lastName) {
        DentistDTO newDTO = new DentistDTO();
        newDTO.setFirstName(firstName);
        newDTO.setLastName(lastName);
        return newDTO;
    }*/

    /*@Test
    public void serviceCanAddAndReturnADentistDTO() {
        String firstName = "Bill";
        String lastName = "Forest";
        DentistDTO testableDTO = this.getTestableDentistDTO(firstName, lastName);
        dentistService.addDentistToDataBase(testableDTO);
        DentistDTO DTOAfterSaving = this.dentistService.findDentistByName(firstName, lastName);
        assertEquals("Returned DTO should have right first name", firstName, DTOAfterSaving.getFirstName());
        assertEquals("Returned DTO should have right last name", lastName, DTOAfterSaving.getLastName());
    }*/

}