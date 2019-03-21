package com.example.codechallenge.validation;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * A single validation step
 * A {@link Validation} as many {@link RowValidation}
 */
public class RowValidation {

    @JsonIgnore
    public static final String CORRECT = "correct";
    public static final String INVALID = "invalid";
    public static final String ADD_TRAILING_ZERO = "add trailing zero";
    public static final String REMOVE_SYMBOLS = "remove symbols";


    private String id;
    private String phoneNumber;
    private List<String> validationResultList = new ArrayList<>();


    public RowValidation() {
    }

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

    /**
     * Gets the validation results list in a immutable way
     * @return a copy of the validation results list
     */
    public List<String> getValidationResultList() {
        return new ArrayList<>(this.validationResultList);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void addValidationResult(String validationResult) {
        this.validationResultList.add(validationResult);
    }
}
