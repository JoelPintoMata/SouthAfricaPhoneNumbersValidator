package com.example.codechallenge.validation;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface for a validation service
 */
public interface ValidationService {

    Validation validate(HttpServletRequest request) throws ValidationException;

    Validation validate(String phoneNumber);
}