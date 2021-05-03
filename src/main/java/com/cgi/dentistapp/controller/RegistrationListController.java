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

/**
 * Controller for registration list pages
 */
@Controller
@EnableAutoConfiguration
public class RegistrationListController {
    @Autowired
    private DentistVisitService dentistVisitService;

    /**
     * Getter for main registrationList page
     * @param searchForm form for making searches
     * @param model Model access
     * @return registrationList page
     */
    @GetMapping("/registrationList")
    public String showRegistrationList(SearchForm searchForm, Model model) {
        model.addAttribute("registrations", this.dentistVisitService.getAllVisits());
        return "registrationList";
    }

    /**
     * Getter for search result page
     * @param searchString String that is searched for
     * @param searchForm form for making a new search
     * @param model model access
     * @return registration list page that lists found results
     */
    @GetMapping("/registrationList/search/{searchString}")
    public String searchForFirstNameDentists(@PathVariable String searchString, SearchForm searchForm, Model model) {
        List<DentistVisitDTO> foundResults = prepareFullListOfSearchResults(searchString);
        model.addAttribute("search", searchString);
        model.addAttribute("registrations", foundResults);
        return "registrationList";
    }

    /**
     * Method for performing registration search through services
     * @param searchString String that is searched for
     * @return a DentistVisitDTO list that matches the search string by dentist or date and time
     */
    private List<DentistVisitDTO> prepareFullListOfSearchResults(String searchString) {
        List<DentistVisitDTO> foundNameVisits = this.dentistVisitService.findByDentistNameContains(searchString);
        List<DentistVisitDTO> foundDateVisits = this.dentistVisitService.findByDateTimeContains(searchString);
        if(!foundDateVisits.isEmpty()) {
            foundNameVisits.addAll(foundDateVisits);
        }
        return foundNameVisits;
    }

    /**
     * Post method for accepting search forms
     * @param searchForm form that contains search string
     * @param bindingResult binding result access
     * @return on error redirects to main list page otherwise to a search result page
     */
    @PostMapping("/registrationList/search")
    public String searchForElement(@Valid SearchForm searchForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/registrationList";
        }
        return "redirect:/registrationList/search/"+searchForm.getSearchString();
    }

    /**
     * Get mapping for a separate search form page
     * @param searchForm search form to be filled out
     * @return a page with only a search box
     */
    @GetMapping("/registrationList/search")
    public String getSearchPage(SearchForm searchForm) {
        return "fragments/searchBox";
    }
}
