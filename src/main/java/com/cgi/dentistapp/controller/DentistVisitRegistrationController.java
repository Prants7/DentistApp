package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.DentistDTO;
import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.dto.VisitationDateDTO;
import com.cgi.dentistapp.dto.VisitationTimeDTO;
import com.cgi.dentistapp.form.SearchForm;
import com.cgi.dentistapp.service.AvailableDateTimeService;
import com.cgi.dentistapp.service.DentistService;
import com.cgi.dentistapp.service.DentistVisitService;
import com.cgi.dentistapp.verification.DentistVisitChecking.exceptions.DentistVisitRegisterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;
import java.util.List;

@Controller
@EnableAutoConfiguration
public class DentistVisitRegistrationController extends WebMvcConfigurerAdapter {
    @Autowired
    private DentistVisitService dentistVisitService;
    @Autowired
    private DentistService dentistService;
    @Autowired
    private AvailableDateTimeService availableDateTimeService;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/registration")
    public String showRegisterForm(DentistVisitDTO dentistVisitDTO, Model model) {
        /*model.addAttribute("dentists", this.dentistService.getAllDentists());
        model.addAttribute("dates", this.availableDateTimeService.getAllVisitationDates());
        model.addAttribute("times", this.availableDateTimeService.getAllVisitationTimes());*/
        this.populateDentistVisitFormModel(model);
        return "form";
    }

    private void populateDentistVisitFormModel(Model model) {
        //model.addAttribute("dentists", this.getAListOfDentistWithAddedFake());
        model.addAttribute("dentists", this.dentistService.getAllDentists());
        //model.addAttribute("dates", this.getListOfDatesWithAddedFake());
        model.addAttribute("dates", this.availableDateTimeService.getAllVisitationDates());
        //model.addAttribute("times", this.getListOfTimesWithAddedFake());
        model.addAttribute("times", this.availableDateTimeService.getAllVisitationTimes());
    }

    private DentistDTO makeFakeDentist() {
        DentistDTO newDentist = new DentistDTO();
        newDentist.setName("Fake Dentist");
        return newDentist;
    }

    private List<DentistDTO> getAListOfDentistWithAddedFake() {
        List<DentistDTO> foundDentists = this.dentistService.getAllDentists();
        foundDentists.add(this.makeFakeDentist());
        return foundDentists;
    }

    private List<VisitationDateDTO> getListOfDatesWithAddedFake() {
        List<VisitationDateDTO> foundDates = this.availableDateTimeService.getAllVisitationDates();
        foundDates.add(new VisitationDateDTO(20,5, 2000));
        return foundDates;
    }

    private List<VisitationTimeDTO> getListOfTimesWithAddedFake() {
        List<VisitationTimeDTO> foundTimes = this.availableDateTimeService.getAllVisitationTimes();
        foundTimes.add(new VisitationTimeDTO(20, 00));
        return foundTimes;
    }

    @PostMapping("/registration")
    public String postRegisterForm(@Valid DentistVisitDTO dentistVisitDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            this.populateDentistVisitFormModel(model);
            return "form";
        }
        try {
            dentistVisitService.addVisit(dentistVisitDTO);
        }
        catch(DentistVisitRegisterException exception) {
            ObjectError error = new ObjectError("globalError", exception.getMessage());
            bindingResult.addError(error);
            this.populateDentistVisitFormModel(model);
            return "form";
        }
        return "redirect:/results";
        /*dentistVisitService.addVisit(dentistVisitDTO);

        return "redirect:/results";*/
    }



}
