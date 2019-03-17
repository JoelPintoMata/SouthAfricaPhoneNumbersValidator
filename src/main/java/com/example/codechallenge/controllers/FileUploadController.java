package com.example.codechallenge.controllers;

import com.example.codechallenge.storage.StorageService;
import com.example.codechallenge.validation.Validation;
import com.example.codechallenge.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
        String result = null;
        try {
            Validation validation = southAfricaValidatorService.validate(request);
            fileSystemStorageService.store(validation);
            result = fileSystemStorageService.loadAsResource(validation.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}