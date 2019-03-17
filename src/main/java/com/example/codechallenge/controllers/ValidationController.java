package com.example.codechallenge.controllers;


import com.example.codechallenge.validation.ValidationException;
import com.example.codechallenge.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Main application controller
 */
@Controller
@EnableAutoConfiguration
public class ValidationController {

    private ValidationService southAfricaValidatorService;

    @Autowired
    public ValidationController(ValidationService southAfricaValidatorService) {
        this.southAfricaValidatorService = southAfricaValidatorService;
    }

    @RequestMapping("/validate")
    @ResponseBody
    public String validate(@RequestParam("phoneNumber") String phoneNumber) {
        String result = null;
        try {
            result = southAfricaValidatorService.validate(phoneNumber).asJson();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        return result;
    }
}