package com.example.codechallenge.validation.southAfrica;

import com.example.codechallenge.validation.RowValidation;
import com.example.codechallenge.validation.Validation;
import com.example.codechallenge.validation.ValidationService;
import org.junit.Assert;
import org.junit.Test;

public class SouthAfricaValidationServiceTest {

    ValidationService validationService = new SouthAfricaValidationService();

    @Test
    public void validatePhoneNumberWithPrefix() {
        Validation validation = validationService.validate("27831234567");
        Assert.assertEquals(validation.getRowValidationList().get(0).getValidationResult(), RowValidation.CORRECT);
        Assert.assertEquals(validation.getTotalLines(), 1);
        Assert.assertEquals(validation.getValidLines(), 1);
        Assert.assertEquals(validation.getInvalidNumbers(), 0);
        Assert.assertEquals(validation.getFixedNunbers(), 0);
    }

    @Test
    public void validatePhoneNumberWithoutPrefix() {
        Assert.assertEquals(validationService.validate("27831234567").getRowValidationList().get(0).getValidationResult(), RowValidation.CORRECT);
    }

    @Test
    public void validatePhoneNumberFixMissingTrailingZero() {
        Assert.assertEquals(validationService.validate("831234567").getRowValidationList().get(0).getValidationResult(), RowValidation.ADD_TRAILING_ZERO);
        Assert.assertEquals(validationService.validate("831234567").getRowValidationList().get(0).getPhoneNumber(), "0831234567");
    }

    @Test
    public void validatePhoneNumberInvalid() {
        Assert.assertEquals(validationService.validate("31234567").getRowValidationList().get(0).getValidationResult(), RowValidation.INVALID);
    }
}