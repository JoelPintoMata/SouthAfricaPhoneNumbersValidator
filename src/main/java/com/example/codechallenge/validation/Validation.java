package com.example.codechallenge.validation;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Validation process class
 */
public class Validation {

    private String id;
    private List<RowValidation> rowValidationList;
    private int totalNumbers;
    private int validNumbers;
    private int invalidNumbers;
    private int fixedNumbers;

    public Validation() {
    }

    public Validation(int validNumbers, int invalidNumbers, int fixedNumbers, List<RowValidation> rowValidationList) {
        this.id = UUID.randomUUID().toString();
        this.totalNumbers = rowValidationList.size();
        this.validNumbers = validNumbers;
        this.invalidNumbers = invalidNumbers;
        this.fixedNumbers = fixedNumbers;
        this.rowValidationList = rowValidationList;
    }

    public String getId() {
        return id;
    }

    public List<RowValidation> getRowValidationList() {
        return rowValidationList;
    }

    public void setRowValidationList(List<RowValidation> rowValidationList) {
        this.rowValidationList = rowValidationList;
    }

    public int getTotalNumbers() {
        return totalNumbers;
    }

    public void setTotalNumbers(int totalNumbers) {
        this.totalNumbers = totalNumbers;
    }

    public int getValidNumbers() {
        return validNumbers;
    }

    public void setValidNumbers(int validNumbers) {
        this.validNumbers = validNumbers;
    }

    public int getInvalidNumbers() {
        return invalidNumbers;
    }

    public void setInvalidNumbers(int invalidNumbers) {
        this.invalidNumbers = invalidNumbers;
    }

    public int getFixedNumbers() {
        return fixedNumbers;
    }

    public void setFixedNumbers(int fixedNumbers) {
        this.fixedNumbers = fixedNumbers;
    }

    /**
     * Formats this {@link Validation} as a json
     * @return this Validation as a json
     * @throws ValidationException
     */
    public String asJson() throws ValidationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (IOException e) {
            throw new ValidationException("Cannot convert Validation to String");
        }
    }
}
