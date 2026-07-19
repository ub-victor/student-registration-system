package com.auca.student_registration_system.constants;

/**
 * Application-wide constants for error messages, validation rules, and system values.
 */
public final class AppConstants {

    private AppConstants() {
        // Private constructor to prevent instantiation
    }

    // ============== API Response Constants ==============
    public static final String API_VERSION = "v1";
    public static final String API_BASE_PATH = "/api/v1";

    // ============== Validation Constants ==============
    public static final int REGISTRATION_NO_MIN_LENGTH = 3;
    public static final int REGISTRATION_NO_MAX_LENGTH = 20;
    public static final int NAME_MIN_LENGTH = 2;
    public static final int NAME_MAX_LENGTH = 100;
    public static final int CODE_MIN_LENGTH = 1;
    public static final int CODE_MAX_LENGTH = 20;
    public static final int CREDITS_MIN = 1;
    public static final int CREDITS_MAX = 12;
    public static final int MAX_SEMESTER_CREDITS = 21;
    public static final int COURSE_CAPACITY_MAX = 500;
    public static final int USERNAME_MIN_LENGTH = 3;
    public static final int USERNAME_MAX_LENGTH = 20;
    public static final int PASSWORD_MIN_LENGTH = 8;

    // ============== Role Constants ==============
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_REGISTRAR = "REGISTRAR";
    public static final String ROLE_LECTURER = "LECTURER";
    public static final String ROLE_STUDENT = "STUDENT";

    // ============== Status Constants ==============
    public static final String SEMESTER_STATUS_ACTIVE = "ACTIVE";
    public static final String SEMESTER_STATUS_PLANNED = "PLANNED";
    public static final String SEMESTER_STATUS_COMPLETED = "COMPLETED";
    public static final String SEMESTER_STATUS_CLOSED = "CLOSED";

    // ============== Academic Unit Type Constants ==============
    public static final String ACADEMIC_UNIT_TYPE_FACULTY = "FACULTY";
    public static final String ACADEMIC_UNIT_TYPE_PROGRAMME = "PROGRAMME";
    public static final String ACADEMIC_UNIT_TYPE_DEPARTMENT = "DEPARTMENT";

    // ============== Error Messages ==============
    public static final String ERROR_NOT_FOUND = "Resource not found";
    public static final String ERROR_DUPLICATE_ENTRY = "Duplicate entry: resource already exists";
    public static final String ERROR_INVALID_REQUEST = "Invalid request data";
    public static final String ERROR_UNAUTHORIZED = "Unauthorized access";
    public static final String ERROR_FORBIDDEN = "Access forbidden";
    public static final String ERROR_INTERNAL_SERVER = "Internal server error";

    // ============== JWT Constants ==============
    public static final long JWT_EXPIRATION = 86400000; // 24 hours in milliseconds
    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_PREFIX = "Bearer ";
}
