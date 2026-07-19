package com.auca.student_registration_system.exception;

/**
 * Base runtime exception for application-specific errors.
 * All custom exceptions should extend this class.
 */
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ApplicationException with specified message.
     *
     * @param message the detail message
     */
    public ApplicationException(String message) {
        super(message);
    }

    /**
     * Constructs a new ApplicationException with specified message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
