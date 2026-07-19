package com.auca.student_registration_system.constants;

/**
 * Enumeration for student registration status.
 */
public enum ERegistrationStatus {
    /**
     * Registered - student is registered for the semester
     */
    REGISTERED("REGISTERED", "Registered"),

    /**
     * Withdrawn - student has withdrawn from the semester
     */
    WITHDRAWN("WITHDRAWN", "Withdrawn"),

    /**
     * Completed - semester registration is complete and results recorded
     */
    COMPLETED("COMPLETED", "Completed");

    private final String value;
    private final String displayName;

    ERegistrationStatus(String value, String displayName) {
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
