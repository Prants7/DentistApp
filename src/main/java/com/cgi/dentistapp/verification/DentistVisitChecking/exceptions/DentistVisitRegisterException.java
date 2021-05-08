package com.cgi.dentistapp.verification.DentistVisitChecking.exceptions;

/**
 * abstract class for all exceptions thrown about bad data in DentistVisitDTO
 */
public abstract class DentistVisitRegisterException extends RuntimeException {

    /**
     * Constructor
     * @param message main message about the exception
     */
    public DentistVisitRegisterException(String message) {
        super(message);
    }
}
