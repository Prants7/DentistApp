package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.DentistVisitDTO;
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

@Controller
@EnableAutoConfiguration
public class RegistrationDetailsController {
    @Autowired
    private DentistVisitService dentistVisitService;
    @Autowired
    private DentistService dentistService;
    @Autowired
    private AvailableDateTimeService availableDateTimeService;

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
        DentistVisitDTO foundVisit = this.dentistVisitService.getVisitById(id);
        if(foundVisit != null) {
            this.dentistVisitService.deleteEntry(id);
        }
        return "redirect:/registrationList";
    }

    @GetMapping("/details/{id}/edit")
    public String getEditOneRegistrationPage(@PathVariable Long id, DentistVisitDTO dentistVisitDTO, Model model) {
        /*DentistVisitDTO foundVisit = this.dentistVisitService.getVisitById(id);
        if(foundVisit == null) {
            return "redirect:/registrationList";
        }
        model.addAttribute("selectedVisit", foundVisit);*/
        if(!addFoundVisitToModel(model, id)) {
            return "redirect:/registrationList";
        }
        /*model.addAttribute("dentists", this.dentistService.getAllDentists());
        model.addAttribute("dates", this.availableDateTimeService.getAllVisitationDates());
        model.addAttribute("times", this.availableDateTimeService.getAllVisitationTimes());*/
        this.populateDentistVisitFormModel(model);
        return "editForm";
    }

    private boolean addFoundVisitToModel(Model model, Long id) {
        DentistVisitDTO foundVisit = this.dentistVisitService.getVisitById(id);
        if(foundVisit == null) {
            return false;
        }
        model.addAttribute("selectedVisit", foundVisit);
        return true;
    }

    @PostMapping("/details/{id}/edit")
    public String editOneRegistration(
            @PathVariable Long id,
            @Valid DentistVisitDTO dentistVisitDTO,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            if(!addFoundVisitToModel(model, id)) {
                return "redirect:/registrationList";
            }
            this.populateDentistVisitFormModel(model);
            return "editForm";
        }
        dentistVisitDTO.setId(id);
        try {
            dentistVisitService.updateVisit(dentistVisitDTO);
        }
        catch(DentistVisitRegisterException exception) {
            ObjectError error = new ObjectError("globalError", exception.getMessage());
            bindingResult.addError(error);
            if(!addFoundVisitToModel(model, id)) {
                return "redirect:/registrationList";
            }
            this.populateDentistVisitFormModel(model);
            return "editForm";
        }
        return "redirect:/details/"+id;
    }

    private void populateDentistVisitFormModel(Model model) {
        model.addAttribute("dentists", this.dentistService.getAllDentists());
        model.addAttribute("dates", this.availableDateTimeService.getAllVisitationDates());
        model.addAttribute("times", this.availableDateTimeService.getAllVisitationTimes());
    }
}
