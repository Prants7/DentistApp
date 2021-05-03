package com.cgi.dentistapp.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for home page
 */
@Controller
@EnableAutoConfiguration
public class HomeController {

    /**
     * Getter for the home page
     * @return html page to display
     */
    @GetMapping("/")
    public String showHomePage() {
        return "homePage";
    }
}
