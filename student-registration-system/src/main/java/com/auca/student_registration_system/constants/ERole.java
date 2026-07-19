package com.auca.student_registration_system.constants;

/**
 * Enumeration for user roles in the system.
 */
public enum ERole {
    /**
     * System administrator
     */
    ADMIN("ADMIN", "System Administrator"),

    /**
     * Registrar - manages registrations
     */
    REGISTRAR("REGISTRAR", "Registrar"),

    /**
     * Lecturer - teaches courses
     */
    LECTURER("LECTURER", "Lecturer"),

    /**
     * Student - enrolls in courses
     */
    STUDENT("STUDENT", "Student");

    private final String value;
    private final String displayName;

    ERole(String value, String displayName) {
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
