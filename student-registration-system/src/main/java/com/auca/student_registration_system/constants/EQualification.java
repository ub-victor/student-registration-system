package com.auca.student_registration_system.constants;

/**
 * Enumeration for teacher qualification levels.
 */
public enum EQualification {
    /**
     * Master's degree
     */
    MASTER("MASTER", "Master"),

    /**
     * PhD degree
     */
    PHD("PHD", "PhD"),

    /**
     * Professor title
     */
    PROFESSOR("PROFESSOR", "Professor");

    private final String value;
    private final String displayName;

    EQualification(String value, String displayName) {
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
