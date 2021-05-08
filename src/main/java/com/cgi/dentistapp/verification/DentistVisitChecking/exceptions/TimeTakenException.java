package com.cgi.dentistapp.verification.DentistVisitChecking.exceptions;

/**
 * Exception thrown when a selected date and time have already been taken for a dentist visit
 */
public class TimeTakenException extends DentistVisitRegisterException {

    public TimeTakenException(String message) {
        super(message);
    }

}
