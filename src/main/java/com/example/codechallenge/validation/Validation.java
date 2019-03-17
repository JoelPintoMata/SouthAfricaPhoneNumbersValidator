package com.example.codechallenge.validation;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class Validation {

    private final UUID id;
    private final List<RowValidation> rowValidationList;
    private int totalLines;
    private final int validLines;
    private final int invalidNumbers;
    private final int fixedNumbers;

    public Validation(int validNumbers, int invalidNumbers, int fixedNumbers, List<RowValidation> rowValidationList) {
        this.id = UUID.randomUUID();
        this.validLines = validNumbers;
        this.invalidNumbers = invalidNumbers;
        this.fixedNumbers = fixedNumbers;
        this.rowValidationList = rowValidationList;
    }

    public String getId() {
        return id.toString();
    }

    public int getTotalLines() {
        return rowValidationList.size();
    }

    public int getValidLines() {
        return validLines;
    }

    public List<RowValidation> getRowValidationList() {
        return rowValidationList;
    }

    public String asJson() throws ValidationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (IOException e) {
            throw new ValidationException("Cannot convert Validation to String");
        }
    }

    public int getInvalidNumbers() {
        return this.invalidNumbers;
    }

    public int getFixedNunbers() {
        return this.fixedNumbers;
    }
}
