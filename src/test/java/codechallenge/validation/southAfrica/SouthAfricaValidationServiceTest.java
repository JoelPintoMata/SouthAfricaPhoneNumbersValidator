package codechallenge.validation.southAfrica;

import codechallenge.validation.RowValidation;
import codechallenge.validation.Validation;
import codechallenge.validation.ValidationService;
import org.junit.Assert;
import org.junit.Test;

public class SouthAfricaValidationServiceTest {

    ValidationService validationService = new SouthAfricaValidationService();

    @Test
    public void validatePhoneNumberWithPrefix() {
        Validation validation = validationService.validate("27831234567");
        Assert.assertTrue(validation.getRowValidationList().get(0).getValidationResultList().contains(RowValidation.CORRECT));
        Assert.assertEquals(validation.getTotalNumbers(), 1);
        Assert.assertEquals(validation.getValidNumbers(), 1);
        Assert.assertEquals(validation.getInvalidNumbers(), 0);
        Assert.assertEquals(validation.getFixedNumbers(), 0);
    }

    @Test
    public void validatePhoneNumberFixMissingTrailingZero() {
        Validation validation = validationService.validate("831234567");
        Assert.assertTrue(validation.getRowValidationList().get(0).getValidationResultList().contains(RowValidation.ADD_TRAILING_ZERO));
        Assert.assertEquals(validation.getRowValidationList().get(0).getPhoneNumber(), "0831234567");
        Assert.assertEquals(validation.getTotalNumbers(), 1);
        Assert.assertEquals(validation.getValidNumbers(), 0);
        Assert.assertEquals(validation.getInvalidNumbers(), 0);
        Assert.assertEquals(validation.getFixedNumbers(), 1);
    }

    @Test
    public void validatePhoneNumberWithSpaceBegin() {
        Validation validation = validationService.validate(" 27831234567");
        Assert.assertTrue(validation.getRowValidationList().get(0).getValidationResultList().contains((RowValidation.REMOVE_SYMBOLS)));
        Assert.assertEquals(validation.getRowValidationList().get(0).getPhoneNumber(), "27831234567");
        Assert.assertEquals(validation.getTotalNumbers(), 1);
        Assert.assertEquals(validation.getValidNumbers(), 0);
        Assert.assertEquals(validation.getInvalidNumbers(), 0);
        Assert.assertEquals(validation.getFixedNumbers(), 1);

        validation = validationService.validate(" 831234567");
        Assert.assertTrue(validation.getRowValidationList().get(0).getValidationResultList().contains((RowValidation.REMOVE_SYMBOLS)));
        Assert.assertTrue(validation.getRowValidationList().get(0).getValidationResultList().contains((RowValidation.ADD_TRAILING_ZERO)));
        Assert.assertEquals(validation.getRowValidationList().get(0).getPhoneNumber(), "0831234567");
        Assert.assertEquals(validation.getTotalNumbers(), 1);
        Assert.assertEquals(validation.getValidNumbers(), 0);
        Assert.assertEquals(validation.getInvalidNumbers(), 0);
        Assert.assertEquals(validation.getFixedNumbers(), 1);
    }

    @Test
    public void validatePhoneNumberWithSymbolsMiddle() {
        Validation validation = validationService.validate("27 - 831234567");
        Assert.assertTrue(validation.getRowValidationList().get(0).getValidationResultList().contains((RowValidation.REMOVE_SYMBOLS)));
        Assert.assertEquals(validation.getRowValidationList().get(0).getPhoneNumber(), "27831234567");
        Assert.assertEquals(validation.getTotalNumbers(), 1);
        Assert.assertEquals(validation.getValidNumbers(), 0);
        Assert.assertEquals(validation.getInvalidNumbers(), 0);
        Assert.assertEquals(validation.getFixedNumbers(), 1);

        validation = validationService.validate("(0)831234567");
        Assert.assertTrue(validation.getRowValidationList().get(0).getValidationResultList().contains((RowValidation.REMOVE_SYMBOLS)));
        Assert.assertEquals(validation.getRowValidationList().get(0).getPhoneNumber(), "0831234567");
        Assert.assertEquals(validation.getTotalNumbers(), 1);
        Assert.assertEquals(validation.getValidNumbers(), 0);
        Assert.assertEquals(validation.getInvalidNumbers(), 0);
        Assert.assertEquals(validation.getFixedNumbers(), 1);
    }

    @Test
    public void validatePhoneNumberWithSpacesEnd() {
        Validation validation = validationService.validate("27831234567 ");
        Assert.assertTrue(validation.getRowValidationList().get(0).getValidationResultList().contains((RowValidation.REMOVE_SYMBOLS)));
        Assert.assertEquals(validation.getRowValidationList().get(0).getPhoneNumber(), "27831234567");
        Assert.assertEquals(validation.getTotalNumbers(), 1);
        Assert.assertEquals(validation.getValidNumbers(), 0);
        Assert.assertEquals(validation.getInvalidNumbers(), 0);
        Assert.assertEquals(validation.getFixedNumbers(), 1);

        validation = validationService.validate("831234567 ");
        Assert.assertTrue(validation.getRowValidationList().get(0).getValidationResultList().contains((RowValidation.REMOVE_SYMBOLS)));
        Assert.assertTrue(validation.getRowValidationList().get(0).getValidationResultList().contains((RowValidation.ADD_TRAILING_ZERO)));
        Assert.assertEquals(validation.getRowValidationList().get(0).getPhoneNumber(), "0831234567");
        Assert.assertEquals(validation.getTotalNumbers(), 1);
        Assert.assertEquals(validation.getValidNumbers(), 0);
        Assert.assertEquals(validation.getInvalidNumbers(), 0);
        Assert.assertEquals(validation.getFixedNumbers(), 1);
    }

    @Test
    public void validatePhoneNumberInvalid() {
        Validation validation = validationService.validate("123");
        Assert.assertTrue(validation.getRowValidationList().get(0).getValidationResultList().contains(RowValidation.INVALID));
        Assert.assertEquals(validation.getTotalNumbers(), 1);
        Assert.assertEquals(validation.getValidNumbers(), 0);
        Assert.assertEquals(validation.getInvalidNumbers(), 1);
        Assert.assertEquals(validation.getFixedNumbers(), 0);
    }

    @Test
    public void validatePhoneNumberWithinDeleted() {
        Validation validation = validationService.validate("27761378661_DELETED_27488991647");
        Assert.assertTrue(validation.getRowValidationList().get(0).getValidationResultList().contains(RowValidation.CORRECT));
        Assert.assertEquals(validation.getRowValidationList().get(0).getPhoneNumber(), "27488991647");
        Assert.assertEquals(validation.getTotalNumbers(), 1);
        Assert.assertEquals(validation.getValidNumbers(), 0);
        Assert.assertEquals(validation.getInvalidNumbers(), 0);
        Assert.assertEquals(validation.getFixedNumbers(), 1);

        validation = validationService.validate("27761378661_DELETED_0488991647");
        Assert.assertTrue(validation.getRowValidationList().get(0).getValidationResultList().contains(RowValidation.CORRECT));
        Assert.assertEquals(validation.getRowValidationList().get(0).getPhoneNumber(), "0488991647");
        Assert.assertEquals(validation.getTotalNumbers(), 1);
        Assert.assertEquals(validation.getValidNumbers(), 0);
        Assert.assertEquals(validation.getInvalidNumbers(), 0);
        Assert.assertEquals(validation.getFixedNumbers(), 1);

        validation = validationService.validate("27761378661_DELETED_488991647");

        Assert.assertTrue(validation.getRowValidationList().get(0).getValidationResultList().contains(RowValidation.ADD_TRAILING_ZERO));
        Assert.assertEquals(validation.getRowValidationList().get(0).getPhoneNumber(), "0488991647");
        Assert.assertEquals(validation.getTotalNumbers(), 1);
        Assert.assertEquals(validation.getValidNumbers(), 0);
        Assert.assertEquals(validation.getInvalidNumbers(), 0);
        Assert.assertEquals(validation.getFixedNumbers(), 1);

        validation = validationService.validate("_DELETED_27488991647");
        Assert.assertTrue(validation.getRowValidationList().get(0).getValidationResultList().contains(RowValidation.CORRECT));
        Assert.assertEquals(validation.getRowValidationList().get(0).getPhoneNumber(), "27488991647");
        Assert.assertEquals(validation.getTotalNumbers(), 1);
        Assert.assertEquals(validation.getValidNumbers(), 0);
        Assert.assertEquals(validation.getInvalidNumbers(), 0);
        Assert.assertEquals(validation.getFixedNumbers(), 1);
    }
}