package com.example.codechallenge.controllers;

import com.example.codechallenge.storage.StorageService;
import com.example.codechallenge.validation.RowValidation;
import com.example.codechallenge.validation.Validation;
import com.example.codechallenge.validation.ValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ValidationControllerTest {

    private MockMvc mvc;

    private JacksonTester<Validation> validationJacksonTester;

    @Mock
    private ValidationService southAfricaValidatorService;

    @Mock
    private StorageService fileSystemStorageService;

    @InjectMocks
    private ValidationController validationController;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(validationController).build();
    }

    @Test
    public void validate() throws Exception {
        List<RowValidation> rowValidationList = new ArrayList<>();
        RowValidation rowValidation = new RowValidation("1", "123456789");
        rowValidationList.add(rowValidation);

        Validation validation = new Validation(1, 0, 0, rowValidationList);
        // given
        given(southAfricaValidatorService.validate("123456789"))
                .willReturn(validation);

        // when
        MockHttpServletResponse response = mvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                .get("/validate").param("phoneNumber", "123456789")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assert.assertEquals(validationJacksonTester.parse(response.getContentAsString()).getObject().getTotalNumbers(), validation.getTotalNumbers());
        Assert.assertEquals(validationJacksonTester.parse(response.getContentAsString()).getObject().getValidNumbers(), validation.getValidNumbers());
        Assert.assertEquals(validationJacksonTester.parse(response.getContentAsString()).getObject().getFixedNumbers(), validation.getFixedNumbers());
        Assert.assertEquals(validationJacksonTester.parse(response.getContentAsString()).getObject().getInvalidNumbers(), validation.getInvalidNumbers());
    }

    @Test
    public void get() throws Exception {
        List<RowValidation> rowValidationList = new ArrayList<>();
        RowValidation rowValidation = new RowValidation("1", "123456789");
        rowValidationList.add(rowValidation);

        Validation validation = new Validation(1, 0, 0, rowValidationList);

        ObjectMapper objectMapper = new ObjectMapper();
        String str = objectMapper.writeValueAsString(validation);
        // given
        given(fileSystemStorageService.get(Mockito.anyString()))
                .willReturn(str);

        // when
        MockHttpServletResponse response = mvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/get").param("id" +
                        "", "123456789")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assert.assertEquals(validationJacksonTester.parse(response.getContentAsString()).getObject().getTotalNumbers(), validation.getTotalNumbers());
        Assert.assertEquals(validationJacksonTester.parse(response.getContentAsString()).getObject().getValidNumbers(), validation.getValidNumbers());
        Assert.assertEquals(validationJacksonTester.parse(response.getContentAsString()).getObject().getFixedNumbers(), validation.getFixedNumbers());
        Assert.assertEquals(validationJacksonTester.parse(response.getContentAsString()).getObject().getInvalidNumbers(), validation.getInvalidNumbers());
    }
}