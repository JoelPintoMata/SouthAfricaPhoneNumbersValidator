package codechallenge.validation;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface for a validation service
 */
public interface ValidationService {

    /**
     * A validation procedure implementation over the data provided via an uploaded file
     * @param request the request containing the phone numbers to validate
     * @return the validation results
     * @throws ValidationException
     */
    Validation validate(HttpServletRequest request) throws ValidationException;

    /**
     * An validation procedure implementation over a single phone number
     * @param phoneNumber the phone number to validate
     * @return the validation results
     */
    Validation validate(String phoneNumber);
}