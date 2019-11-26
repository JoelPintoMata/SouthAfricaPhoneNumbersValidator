package codechallenge.controllers;

import codechallenge.storage.StorageException;
import codechallenge.storage.StorageService;
import codechallenge.validation.Validation;
import codechallenge.validation.ValidationException;
import codechallenge.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

/**
 * File upload controller
 * Manages the file upload for validation
 */
@Controller
public class FileUploadController {

    private StorageService fileSystemStorageService;

    private ValidationService southAfricaValidatorService;

    @Autowired
    public FileUploadController(StorageService fileSystemStorageService, ValidationService southAfricaValidatorService) {
        this.fileSystemStorageService = fileSystemStorageService;
        this.southAfricaValidatorService = southAfricaValidatorService;
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(final HttpServletRequest request) {
        String result;
        try {
            Validation validation = southAfricaValidatorService.validate(request);
            fileSystemStorageService.upload(validation);
            result = fileSystemStorageService.get("bad input");
            return result;
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Error while performing file validation: %s", e.getMessage()));
        } catch (StorageException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Error while storing file validation results: %s", e.getMessage()));
        }
    }
}