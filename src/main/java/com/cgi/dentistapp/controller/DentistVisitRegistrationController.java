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

/**
 * Controller that deals with pages for registering new Dentist Visits
 */
@Controller
@EnableAutoConfiguration
public class DentistVisitRegistrationController extends WebMvcConfigurerAdapter {
    @Autowired
    private DentistVisitService dentistVisitService;
    @Autowired
    private DentistService dentistService;
    @Autowired
    private AvailableDateTimeService availableDateTimeService;

    /**
     * For adding a results page that is displayed after a succesful registration
     * @param registry registry for views
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    /**
     * Get method for the registration page
     * @param visitForm Form for new visit
     * @param model model for template elements
     * @return form page
     */
    @GetMapping("/registration")
    public String showRegisterForm(DentistVisitForm visitForm, Model model) {
        return prepareAndGiveFormPage(model);
    }

    /**
     * Method for setting up the model and then returning form page
     * @param model model access
     * @return form page
     */
    private String prepareAndGiveFormPage(Model model) {
        this.populateDentistVisitFormModel(model);
        return "form";
    }

    /**
     * method that groups together all model elements for form page
     * @param model model access
     */
    private void populateDentistVisitFormModel(Model model) {
        model.addAttribute("dentists", this.dentistService.getAllDentists());
        model.addAttribute("dates", this.availableDateTimeService.getAllVisitationDates());
        model.addAttribute("times", this.availableDateTimeService.getAllVisitationTimes());
    }

    /**
     * Post method for accepting new filled out registration forms
     * @param dentistVisitForm filled out visit form
     * @param bindingResult form binding results
     * @param model model access
     * @return if error, kept on page, if successful redirect to results page
     */
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

    /**
     * Method that adds an exception as a global error to the page
     * @param exception exception to add
     * @param bindingResult binding result where errors are added
     */
    private void addGlobalExceptionToPage(Exception exception, BindingResult bindingResult) {
        ObjectError error = new ObjectError("globalError", exception.getMessage());
        bindingResult.addError(error);
    }




}
