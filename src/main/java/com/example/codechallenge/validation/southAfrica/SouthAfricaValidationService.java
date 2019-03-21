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

/**
 * Concrete implementation of South Africa numbers validation service
 */
@Service
public class SouthAfricaValidationService implements ValidationService {

    private Logger logger = LoggerFactory.getLogger(SouthAfricaValidationService.class);

    private static final String SOUTH_AFRICA_PREFIX = "27";
    private static final String SOUTH_AFRICA_TRAILING_DIGIT = "0";

    private final String regex = "((0|27){0,1}[0-9]{9})";
    private final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

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
                if (!rowValidation.getValidationResult().equals(RowValidation.CORRECT)) {
                    fix(rowValidation);
                }
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
        if (!rowValidation.getValidationResult().equals(RowValidation.CORRECT)) {
            fix(rowValidation);
        }
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
        Matcher matcher = pattern.matcher(phoneNumber);

        String matcherGroup2 = null;
        String matcherGroup3 = null;
//        advance until the last match
        while (matcher.find()) {
            matcherGroup2 = matcher.group(1);
            matcherGroup3 = matcher.group(2);
        }
        if (matcherGroup3 != null && (matcherGroup3.equals(SOUTH_AFRICA_TRAILING_DIGIT) || matcherGroup3.equals(SOUTH_AFRICA_PREFIX))) {
            rowValidation.setValidationResult(RowValidation.CORRECT);
            rowValidation.setPhoneNumber(matcherGroup2);
            validLines++;
        } else if (matcherGroup3 == null) {
            rowValidation.setValidationResult(RowValidation.ADD_TRAILING_ZERO);
            rowValidation.setPhoneNumber(String.format("%s%s", SOUTH_AFRICA_TRAILING_DIGIT, matcherGroup2));
            fixedlines++;
        } else {
            rowValidation.setValidationResult(RowValidation.INVALID);
            invalidLines++;
        }
    }

    /**
     * Fixes the phone number on this row validation
     * @param rowValidation
     */
    private void fix(RowValidation rowValidation) {
        if (rowValidation.getPhoneNumber().length() == 9) {
            rowValidation.setPhoneNumber(String.format("0%s", rowValidation.getPhoneNumber()));
            rowValidation.setValidationResult(RowValidation.ADD_TRAILING_ZERO);
        }
    }
}