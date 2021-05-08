package com.cgi.dentistapp.verification.DentistVisitChecking.exceptions;

/**
 * Exception thrown when the Time selected for a dentist visit is not allowed
 */
public class NotAllowedTimeException extends DentistVisitRegisterException {

    public NotAllowedTimeException(String message) {
        super(message);
    }
}
