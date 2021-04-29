package com.cgi.dentistapp.form;

import javax.validation.constraints.Size;

public class SearchForm {
    @Size(min = 1, max = 50)
    String dentistNameSearch;

    public String getDentistNameSearch() {
        return dentistNameSearch;
    }

    public void setDentistNameSearch(String dentistNameSearch) {
        this.dentistNameSearch = dentistNameSearch;
    }
}
