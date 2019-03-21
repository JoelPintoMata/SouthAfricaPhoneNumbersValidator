package com.example.codechallenge.validation.southAfrica;

import com.example.codechallenge.validation.RowValidation;
import com.example.codechallenge.validation.Validation;
import com.example.codechallenge.validation.ValidationException;
import com.example.codechallenge.validation.ValidationService;
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

import static com.example.codechallenge.validation.RowValidation.REMOVE_SYMBOLS;

/**
 * Concrete implementation of South Africa numbers validation service
 */
@Service
public class SouthAfricaValidationService implements ValidationService {

    private Logger logger = LoggerFactory.getLogger(SouthAfricaValidationService.class);

    private static final String SOUTH_AFRICA_PREFIX = "27";
    private static final String SOUTH_AFRICA_TRAILING_DIGIT = "0";

    private final String regexValidation = "((0|27){0,1}[0-9]{9})";
    private final String regexCleanup = "[-()\\s]";

    private final Pattern patternValidation = Pattern.compile(regexValidation, Pattern.MULTILINE);
    private final Pattern patternCleanup = Pattern.compile(regexCleanup, Pattern.MULTILINE);

    private int validLines;
    private int invalidLines;
    private int fixedlines;

    @Override
    public Validation validate(HttpServletRequest request) throws ValidationException {
        boolean skip = true;

        List<RowValidation> rowValidationList = new ArrayList<>();

        try (InputStream is = request.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while(reader.ready()) {
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
     * Checks if the phone number included on this RowValidator is valid
     * @param rowValidation the row to validate
     * @return true if valid, false, otherwise
     */
    private void validate(RowValidation rowValidation) {
        String phoneNumber = rowValidation.getPhoneNumber();
        Matcher matcherValidation = patternValidation.matcher(phoneNumber);

        String matchedValidationString = null;
//        advance until the last match
        while (matcherValidation.find()) {
            phoneNumber = matcherValidation.group(1);
            matchedValidationString = matcherValidation.group(2);
        }

//        solve issues with symbols
        Matcher matcherCleanup = patternCleanup.matcher(phoneNumber);
        if (matcherCleanup.find()) {
            phoneNumber = phoneNumber.replaceAll(regexCleanup, "");

            rowValidation.setPhoneNumber(phoneNumber);
            rowValidation.addValidationResult(RowValidation.REMOVE_SYMBOLS);
            fixedlines++;
        }

//        solve issues with prefixes
        if (matchedValidationString != null && (matchedValidationString.equals(SOUTH_AFRICA_TRAILING_DIGIT) || matchedValidationString.equals(SOUTH_AFRICA_PREFIX))) {
            rowValidation.addValidationResult(RowValidation.CORRECT);
            rowValidation.setPhoneNumber(phoneNumber);
            validLines++;
        } else if (matchedValidationString == null) {
            rowValidation.addValidationResult(RowValidation.ADD_TRAILING_ZERO);
            rowValidation.setPhoneNumber(String.format("%s%s", SOUTH_AFRICA_TRAILING_DIGIT, phoneNumber));
            fixedlines++;
        } else {
            rowValidation.addValidationResult(RowValidation.INVALID);
            invalidLines++;
        }
    }
}