package com.auca.student_registration_system.constants;

public enum EAcademicUnitType {
    FACULTY("FACULTY", "Faculty"),
    PROGRAMME("PROGRAMME", "Programme"),
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
