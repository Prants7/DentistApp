package com.cgi.dentistapp.form;

import javax.validation.constraints.Size;

/**
 * Form for making dentistVisit searches
 */
public class SearchForm {
    @Size(min = 1, max = 50)
    String searchString;

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
