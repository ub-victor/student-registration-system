package com.auca.student_registration_system.constants;

/**
 * Enumeration for semester status in the academic calendar.
 */
public enum ESemesterStatus {
    /**
     * Planned - semester is planned but not yet started
     */
    PLANNED("PLANNED", "Planned"),

    /**
     * Active - semester is currently active and accepting registrations
     */
    ACTIVE("ACTIVE", "Active"),

    /**
     * Completed - semester has ended, results can now be entered
     */
    COMPLETED("COMPLETED", "Completed"),

    /**
     * Closed - semester is closed, no more changes allowed
     */
    CLOSED("CLOSED", "Closed");

    private final String value;
    private final String displayName;

    ESemesterStatus(String value, String displayName) {
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
