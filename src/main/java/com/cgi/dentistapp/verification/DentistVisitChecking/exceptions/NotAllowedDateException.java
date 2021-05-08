package com.cgi.dentistapp.verification.DentistVisitChecking.exceptions;

/**
 * Exception thrown when the designated date on a dentist visit is not allowed
 */
public class NotAllowedDateException extends DentistVisitRegisterException {

    public NotAllowedDateException(String message) {
        super(message);
    }
}
