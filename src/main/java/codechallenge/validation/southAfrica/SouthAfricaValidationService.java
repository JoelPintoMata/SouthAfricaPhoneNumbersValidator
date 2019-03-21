package codechallenge.validation.southAfrica;

import codechallenge.validation.RowValidation;
import codechallenge.validation.Validation;
import codechallenge.validation.ValidationException;
import codechallenge.validation.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Concrete implementation of South Africa numbers validation service
 */
@Service
public class SouthAfricaValidationService implements ValidationService {

    private Logger logger = LoggerFactory.getLogger(SouthAfricaValidationService.class);

    private static final String SOUTH_AFRICA_PREFIX = "27";
    private static final String SOUTH_AFRICA_TRAILING_DIGIT = "0";
    private static final int SOUTH_AFRICA_NUMBER_OF_DIGITS = 9;

    private final String regexValidation = "(("+SOUTH_AFRICA_TRAILING_DIGIT+"|"+SOUTH_AFRICA_PREFIX+"){0,1}[0-9]{9})$";
    private final String regExWithTrailingZero = "(("+SOUTH_AFRICA_TRAILING_DIGIT+"){1}[0-9]{9})$";
    private final String regExWithPrefix = "(("+SOUTH_AFRICA_PREFIX+"){1}[0-9]{"+SOUTH_AFRICA_NUMBER_OF_DIGITS+"})$";
    private final String regExWithoutTrailingZeroOrPrefix = "^[0-9]{"+SOUTH_AFRICA_NUMBER_OF_DIGITS+"}$";
    private final String regexCleanup = "[-()\\s]";
    private final String regexRightSideNumber = "(_DELETED_)(.*)$";

    private final Pattern patternValidation = Pattern.compile(regexValidation, Pattern.MULTILINE);
    private final Pattern patternWithTrailingZero = Pattern.compile(regExWithTrailingZero, Pattern.MULTILINE);
    private final Pattern patternWithPrefix = Pattern.compile(regExWithPrefix, Pattern.MULTILINE);
    private final Pattern patternWithoutTrailingZeroOrPrefix = Pattern.compile(regExWithoutTrailingZeroOrPrefix, Pattern.MULTILINE);
    private final Pattern patternCleanup = Pattern.compile(regexCleanup, Pattern.MULTILINE);
    private final Pattern patternRightSideNumber = Pattern.compile(regexRightSideNumber, Pattern.MULTILINE);

    private int validLines;
    private int invalidLines;
    private int fixedlines;

    @Override
    public Validation validate(HttpServletRequest request) throws ValidationException {

        List<RowValidation> rowValidationList = new ArrayList<>();

        try (InputStream is = request.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            boolean skip = true;
            while(reader.ready()) {
//                lets skip the header
                if (skip) {
                    skip = false;
                    continue;
                }

                String[] row = reader.readLine().split(",");

                RowValidation rowValidation = new RowValidation(row[0], row[1]);
                validate(rowValidation);
                rowValidationList.add(rowValidation);
            }
        } catch (IOException e) {
            logger.error(String.format("Failed to perform validation: %s", e.getMessage()));
            throw new ValidationException("Failed to store file ", e);
        }
        return new Validation(validLines, invalidLines, fixedlines, rowValidationList);
    }

    @Override
    public Validation validate(String phoneNumber) {
        List<RowValidation> rowValidationList = new ArrayList<>();

        RowValidation rowValidation = new RowValidation(phoneNumber);
        validate(rowValidation);
        rowValidationList.add(rowValidation);

        return new Validation(validLines, invalidLines, fixedlines, rowValidationList);
    }

    /**
     * Performs a {@link RowValidation} phone number validation
     * @param rowValidation the row to validate
     * @return true if valid, false, otherwise
     */
    private void validate(RowValidation rowValidation) {

        parse(rowValidation);

        sanitize(rowValidation);
        String phoneNumber = rowValidation.getPhoneNumber();

        Matcher matcherValidation = patternValidation.matcher(phoneNumber);
        boolean isValid = false;
        if (matcherValidation.find()) {
            logger.info(String.format("Phone number is valid: %s", phoneNumber));
            isValid = true;
        }

        Matcher matcherWithTrailingZero = patternWithTrailingZero.matcher(phoneNumber);
        Matcher matcherWithPrefix = patternWithPrefix.matcher(phoneNumber);

        if (matcherWithTrailingZero.find() || matcherWithPrefix.find()) {
            logger.info(String.format("Phone number is correct: %s", phoneNumber));
            rowValidation.addValidationResult(RowValidation.CORRECT);
            rowValidation.setPhoneNumber(phoneNumber);
            validLines++;
        }

        Matcher matcherWithoutTrailingZeroOrPrefix = patternWithoutTrailingZeroOrPrefix.matcher(phoneNumber);
        if (matcherWithoutTrailingZeroOrPrefix.find()) {
            logger.info(String.format("Phone number missing trailing 0: %s", phoneNumber));
            rowValidation.addValidationResult(RowValidation.ADD_TRAILING_ZERO);
            rowValidation.setPhoneNumber(String.format("%s%s", SOUTH_AFRICA_TRAILING_DIGIT, phoneNumber));
            fixedlines++;
        } else if (!isValid) {
            logger.info(String.format("Invalid phone number: %s", phoneNumber));
            rowValidation.addValidationResult(RowValidation.INVALID);
            rowValidation.setPhoneNumber(String.format("%s%s", SOUTH_AFRICA_TRAILING_DIGIT, phoneNumber));
            invalidLines++;
        }
    }

    /**
     * Performs a {@link RowValidation} parsing by extract and setting the phone number
     * @param rowValidation the row validation
     */
    private void parse(RowValidation rowValidation) {
        String phoneNumber = rowValidation.getPhoneNumber();
        if (phoneNumber.contains("_DELETED_")) {
            Matcher matcherRightSideNumber = patternRightSideNumber.matcher(phoneNumber);
            if (matcherRightSideNumber.find()) {
                phoneNumber = matcherRightSideNumber.group(2);
            }
        } else {
            phoneNumber = rowValidation.getPhoneNumber();
        }
        rowValidation.setPhoneNumber(phoneNumber);
    }

    /**
     * Performs a {@link RowValidation} sanitizing by removing all symbols from the phone number
     * @param rowValidation the row validation
     */
    private void sanitize(RowValidation rowValidation) {
        String phoneNumber = rowValidation.getPhoneNumber();
        Matcher matcherCleanup = patternCleanup.matcher(phoneNumber);
        if (matcherCleanup.find()) {
            phoneNumber = phoneNumber.replaceAll(regexCleanup, "");

            logger.info(String.format("Phone number contained symbols: %s", phoneNumber));
            rowValidation.setPhoneNumber(phoneNumber);
            rowValidation.addValidationResult(RowValidation.REMOVE_SYMBOLS);
            fixedlines++;
        }
    }
}