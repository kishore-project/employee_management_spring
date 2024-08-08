package com.ideas2it.employeemanagement.exceptions;

import org.hibernate.HibernateException;

/**
 * This class handles custom exceptions related to database operations.
 * Extends HibernateExceptions to provide more specific error handling.
 * @author kishore
 */
public class EmployeeException extends HibernateException {
    /**
     * Contructs a DatabaseException with the specified details message and cause.
     *
     * @param message the details message.
     * @param error the cause (which is saved for later retrival by the method).
     */
    public EmployeeException(String message, Throwable error) {
        super(message, error);
    }
}
