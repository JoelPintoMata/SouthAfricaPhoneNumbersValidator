package com.example.codechallenge.validation;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A single validation step
 * A {@link Validation} as many {@link RowValidation}
 */
public class RowValidation {

    @JsonIgnore
    public static final String CORRECT = "correct";
    public static final String INVALID = "invalid";
    public static final String ADD_TRAILING_ZERO = "add trailing zero" ;

    private final String id;
    private String phoneNumber;
    private String validationResult;

    public RowValidation(String phoneNumber) {
        this.id = new String();
        this.phoneNumber = phoneNumber;
    }

    public RowValidation(String id, String phoneNumber) {
        this.id = id;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getValidationResult() {
        return validationResult;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setValidationResult(String validationResult) {
        this.validationResult = validationResult;
    }
}
