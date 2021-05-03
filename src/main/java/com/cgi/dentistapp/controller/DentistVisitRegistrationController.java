package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.form.DentistVisitForm;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;

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
    public String showRegisterForm(DentistVisitForm visitForm, Model model) {
        return prepareAndGiveFormPage(model);
    }

    private String prepareAndGiveFormPage(Model model) {
        this.populateDentistVisitFormModel(model);
        return "form";
    }

    private void populateDentistVisitFormModel(Model model) {
        model.addAttribute("dentists", this.dentistService.getAllDentists());
        model.addAttribute("dates", this.availableDateTimeService.getAllVisitationDates());
        model.addAttribute("times", this.availableDateTimeService.getAllVisitationTimes());
    }

    @PostMapping("/registration")
    public String postRegisterForm(@Valid DentistVisitForm dentistVisitForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return prepareAndGiveFormPage(model);
        }
        try {
            dentistVisitService.addVisit(new DentistVisitDTO(dentistVisitForm));
        }
        catch(DentistVisitRegisterException exception) {
            addGlobalExceptionToPage(exception, bindingResult);
            return prepareAndGiveFormPage(model);
        }
        return "redirect:/results";
    }

    private void addGlobalExceptionToPage(Exception exception, BindingResult bindingResult) {
        ObjectError error = new ObjectError("globalError", exception.getMessage());
        bindingResult.addError(error);
    }




}
