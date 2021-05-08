package com.cgi.dentistapp.verification.DentistVisitChecking.exceptions;

/**
 * Exception thrown when a dentist is not found in te database
 */
public class DentistNotFoundException extends DentistVisitRegisterException {

    public DentistNotFoundException(String message) {
        super(message);
    }
}
