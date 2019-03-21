package codechallenge.controllers;


import codechallenge.storage.StorageException;
import codechallenge.storage.StorageService;
import codechallenge.validation.ValidationException;
import codechallenge.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Validation controller
 */
@Controller
public class ValidationController {

    private ValidationService southAfricaValidatorService;

    private StorageService fileSystemStorageService;

    @Autowired
    public ValidationController(ValidationService southAfricaValidatorService, StorageService fileSystemStorageService) {
        this.southAfricaValidatorService = southAfricaValidatorService;
        this.fileSystemStorageService = fileSystemStorageService;
    }

    /**
     * Validates a single phone number
     * @param phoneNumber the phone number
     * @return the validation result
     */
    @GetMapping("/validate")
    @ResponseBody
    public String validate(@RequestParam("phoneNumber") String phoneNumber) {
        String result;
        try {
            result = southAfricaValidatorService.validate(phoneNumber).asJson();
            return result;
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while performing file validation: ", e);
        }
    }

    /**
     * Retrieves a validation result
     * @param id the validation id
     * @return the validation result
     */
    @GetMapping("/get")
    @ResponseBody
    public String get(@RequestParam("id") String id) {
        String result;
        try {
            result =  fileSystemStorageService.get(id);
            return result;
        } catch (StorageException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while storing file validation results: ", e);
        }
    }
}