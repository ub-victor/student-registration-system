package com.auca.student_registration_system.util;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Generic API response wrapper for consistent API responses.
 *
 * @param <T> The type of data being returned
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private String timestamp;
    private String path;

    /**
     * Default constructor
     */
    public ApiResponse() {
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    /**
     * Constructor with success status and message
     *
     * @param success indicates if the request was successful
     * @param message response message
     */
    public ApiResponse(boolean success, String message) {
        this();
        this.success = success;
        this.message = message;
    }

    /**
     * Constructor with all fields
     *
     * @param success indicates if the request was successful
     * @param message response message
     * @param data the response data
     */
    public ApiResponse(boolean success, String message, T data) {
        this(success, message);
        this.data = data;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
