package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.form.SearchForm;
import com.cgi.dentistapp.service.DentistVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@EnableAutoConfiguration
public class RegistrationListController {

    @Autowired
    private DentistVisitService dentistVisitService;


    @GetMapping("/registrationList")
    public String showRegistrationList(SearchForm searchForm, Model model) {
        model.addAttribute("registrations", this.dentistVisitService.getAllVisits());
        return "registrationList";
    }

    @GetMapping("/registrationList/search/{searchString}")
    public String searchForFirstNameDentists(@PathVariable String searchString, SearchForm searchForm, Model model) {
        List<DentistVisitDTO> foundResults = prepareFullListOfSearchResults(searchString);
        model.addAttribute("search", searchString);
        model.addAttribute("registrations", foundResults);
        return "registrationList";
    }

    private List<DentistVisitDTO> prepareFullListOfSearchResults(String searchString) {
        List<DentistVisitDTO> foundNameVisits = this.dentistVisitService.findByDentistNameContains(searchString);
        List<DentistVisitDTO> foundDateVisits = this.dentistVisitService.findByDateTimeContains(searchString);
        if(!foundDateVisits.isEmpty()) {
            foundNameVisits.addAll(foundDateVisits);
        }
        return foundNameVisits;
    }

    @PostMapping("/registrationList/search")
    public String searchForElement(@Valid SearchForm searchForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/registrationList";
        }
        return "redirect:/registrationList/search/"+searchForm.getDentistNameSearch();
    }

    @GetMapping("/registrationList/search")
    public String getSearchPage(SearchForm searchForm) {
        return "fragments/searchBox";
    }
}
