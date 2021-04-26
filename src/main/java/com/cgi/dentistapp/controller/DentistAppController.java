package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.service.AvailableDateTimeService;
import com.cgi.dentistapp.service.DentistService;
import com.cgi.dentistapp.service.DentistVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;

@Controller
@EnableAutoConfiguration
public class DentistAppController extends WebMvcConfigurerAdapter {

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

    @GetMapping("/")
    public String showRegisterForm(DentistVisitDTO dentistVisitDTO, Model model) {
        model.addAttribute("dentists", this.dentistService.getAllDentists());
        model.addAttribute("dates", this.availableDateTimeService.getAllVisitationDates());
        model.addAttribute("times", this.availableDateTimeService.getAllVisitationTimes());
        return "form";
    }

    @PostMapping("/")
    public String postRegisterForm(@Valid DentistVisitDTO dentistVisitDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        dentistVisitService.addVisit(dentistVisitDTO);

        return "redirect:/results";
    }

    @GetMapping("/registrationList")
    public String showRegistrationList(Model model) {
        model.addAttribute("registrations", this.dentistVisitService.getAllVisits());
        return "registrationList";
    }

    @GetMapping("/details/{id}")
    public String getRegistrationDetailsPage(@PathVariable Long id, Model model) {
        DentistVisitDTO foundVisit = this.dentistVisitService.getVisitById(id);
        if(foundVisit == null) {
            return "redirect:/registrationList";
        }
        model.addAttribute("selectedVisit", foundVisit);
        return "registrationDetails";
    }

    @PostMapping("/details/{id}/delete")
    public String deleteOneRegistrationPage(@PathVariable Long id, Model model) {
        System.out.println("trying to delete");
        DentistVisitDTO foundVisit = this.dentistVisitService.getVisitById(id);
        if(foundVisit != null) {
            this.dentistVisitService.deleteEntry(id);
        }
        return "redirect:/registrationList";
    }
}
