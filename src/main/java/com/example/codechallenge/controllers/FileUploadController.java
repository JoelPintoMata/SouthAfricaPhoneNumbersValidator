package com.example.codechallenge.controllers;

import com.example.codechallenge.storage.StorageException;
import com.example.codechallenge.storage.StorageService;
import com.example.codechallenge.validation.Validation;
import com.example.codechallenge.validation.ValidationException;
import com.example.codechallenge.validation.ValidationService;
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
            result = fileSystemStorageService.get(validation.getId());
            return result;
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while performing file validation: ", e);
        } catch (StorageException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while storing file validation results: ", e);
        }
    }
}