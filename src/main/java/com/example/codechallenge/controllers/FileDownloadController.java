package com.example.codechallenge.controllers;

import com.example.codechallenge.storage.StorageService;
import com.example.codechallenge.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileDownloadController {

    private StorageService fileSystemStorageService;

    private ValidationService southAfricaValidatorService;

    @Autowired
    public FileDownloadController(StorageService fileSystemStorageService, ValidationService southAfricaValidatorService) {
        this.fileSystemStorageService = fileSystemStorageService;
        this.southAfricaValidatorService = southAfricaValidatorService;
    }

    @RequestMapping("/load")
    @ResponseBody
    public String load(@RequestParam("id") String id) {
        String result = null;
        try {
            result =  fileSystemStorageService.load(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}