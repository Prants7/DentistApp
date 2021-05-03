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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * Controller for displaying detail pages about registered Dentist Visits
 */
@Controller
@EnableAutoConfiguration
public class RegistrationDetailsController {
    @Autowired
    private DentistVisitService dentistVisitService;
    @Autowired
    private DentistService dentistService;
    @Autowired
    private AvailableDateTimeService availableDateTimeService;

    /**
     * Getter for different detail pages
     * @param id id for the registration to display
     * @param model model access
     * @return pages that displays registration details or redirect to full list if id is not used
     */
    @GetMapping("/details/{id}")
    public String getRegistrationDetailsPage(@PathVariable Long id, Model model) {
        DentistVisitDTO foundVisit = this.dentistVisitService.getVisitById(id);
        if(foundVisit == null) {
            return "redirect:/registrationList";
        }
        model.addAttribute("selectedVisit", foundVisit);
        return "registrationDetails";
    }

    /**
     * Method for deleting specific registrations
     * @param id id of the registration to delete
     * @param model model access
     * @return redirects to registration list
     */
    @PostMapping("/details/{id}/delete")
    public String deleteOneRegistrationPage(@PathVariable Long id, Model model) {
        DentistVisitDTO foundVisit = this.dentistVisitService.getVisitById(id);
        if(foundVisit != null) {
            this.dentistVisitService.deleteEntry(id);
        }
        return "redirect:/registrationList";
    }

    /**
     * Get method for editing a registration entry
     * @param id id of the registration to be changed
     * @param dentistVisitForm form for choosing changes
     * @param model model access
     * @return if id is not found, redirected to registration list, otherwise gives the edit page
     */
    @GetMapping("/details/{id}/edit")
    public String getEditOneRegistrationPage(@PathVariable Long id, DentistVisitForm dentistVisitForm, Model model) {
        if(!addSelectedVisitToModel(model, id)) {
            return "redirect:/registrationList";
        }
        return prepareAndReturnEditPage(model);
    }

    /**
     * Adds selected visit value to page model
     * @param model model access
     * @param id id of the selected visit
     * @return false if it wasn't possible to add selected visit, true if it was added
     */
    private boolean addSelectedVisitToModel(Model model, Long id) {
        DentistVisitDTO selectedVisit = this.dentistVisitService.getVisitById(id);
        if(selectedVisit == null) {
            return false;
        }
        model.addAttribute("selectedVisit", selectedVisit);
        return true;
    }

    /**
     * Method for preparing model and then returning the edit page
     * @param model model access
     * @return editForm page
     */
    private String prepareAndReturnEditPage(Model model) {
        this.populateDentistVisitFormModel(model);
        return "editForm";
    }

    /**
     * Method for providing a registration form with all needed model attributes
     * @param model model access
     */
    private void populateDentistVisitFormModel(Model model) {
        model.addAttribute("dentists", this.dentistService.getAllDentists());
        model.addAttribute("dates", this.availableDateTimeService.getAllVisitationDates());
        model.addAttribute("times", this.availableDateTimeService.getAllVisitationTimes());
    }

    /**
     * Post method for accepting a filled out edit form
     * @param id id of the registration that will be changed
     * @param dentistVisitForm Form with new values
     * @param bindingResult binding result access
     * @param model model access
     * @return on wrong id, redirect to list, on form errors back to edit page and on success redirect to details page
     */
    @PostMapping("/details/{id}/edit")
    public String editOneRegistration(
            @PathVariable Long id,
            @Valid DentistVisitForm dentistVisitForm,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            if(!addSelectedVisitToModel(model, id)) {
                return "redirect:/registrationList";
            }
            return prepareAndReturnEditPage(model);
        }
        DentistVisitDTO visitDTO = prepareDTOFromFormAndId(dentistVisitForm, id);
        try {
            dentistVisitService.updateVisit(visitDTO);
        }
        catch(DentistVisitRegisterException exception) {
            addGlobalExceptionToPage(exception, bindingResult);
            if(!addSelectedVisitToModel(model, id)) {
                return "redirect:/registrationList";
            }
            return prepareAndReturnEditPage(model);
        }
        return "redirect:/details/"+id;
    }

    /**
     * Method that makes a DentistVisitDTO from a visitForm and target id
     * @param dentistVisitForm form with data
     * @param id id of the registration to change
     * @return DTO based on input data
     */
    private DentistVisitDTO prepareDTOFromFormAndId(DentistVisitForm dentistVisitForm, Long id) {
        DentistVisitDTO visitDTO = new DentistVisitDTO(dentistVisitForm);
        visitDTO.setId(id);
        return visitDTO;
    }

    /**
     * Method for adding an server side exception as a global error to a page
     * @param exception Exception to be added
     * @param bindingResult Binding result where the exception can be added to
     */
    private void addGlobalExceptionToPage(Exception exception, BindingResult bindingResult) {
        ObjectError error = new ObjectError("globalError", exception.getMessage());
        bindingResult.addError(error);
    }


}
