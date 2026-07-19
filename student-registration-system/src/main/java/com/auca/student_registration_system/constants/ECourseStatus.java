package com.auca.student_registration_system.constants;

/**
 * Enumeration for course status.
 */
public enum ECourseStatus {
    /**
     * Offered - course is offered in the semester
     */
    OFFERED("OFFERED", "Offered"),

    /**
     * Closed - course enrollment is closed
     */
    CLOSED("CLOSED", "Closed"),

    /**
     * Cancelled - course is cancelled
     */
    CANCELLED("CANCELLED", "Cancelled");

    private final String value;
    private final String displayName;

    ECourseStatus(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public String getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }
}
