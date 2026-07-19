package com.auca.student_registration_system.constants;

/**
 * Enumeration for academic unit types in the university hierarchy.
 */
public enum EAcademicUnitType {
    /**
     * Faculty - top level of academic organization
     */
    FACULTY("FACULTY", "Faculty"),

    /**
     * Programme - middle level, represents academic program/degree
     */
    PROGRAMME("PROGRAMME", "Programme"),

    /**
     * Department - lowest level, represents specific department
     */
    DEPARTMENT("DEPARTMENT", "Department");

    private final String value;
    private final String displayName;

    EAcademicUnitType(String value, String displayName) {
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
