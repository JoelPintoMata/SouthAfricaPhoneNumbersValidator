package com.example.codechallenge.controllers;

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
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.reactive.server.XpathAssertions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(MockitoJUnitRunner.class)
public class ValidationControllerTest {

    private MockMvc mvc;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<Validation> validationJacksonTester;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private ValidationController validationController;

    @Before
    public void setup() {
        // We would need this line if we would not use MockitoJUnitRunner
        // MockitoAnnotations.initMocks(this);
        // Initializes the JacksonTester
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(validationController)
                .build();
    }

    @Test
    public void validate() throws Exception {
        List<RowValidation> rowValidationList = new ArrayList<>();
        RowValidation rowValidation = new RowValidation("1", "123456789");
        rowValidationList.add(rowValidation);

        // given
        given(validationService.validate("123456789"))
                .willReturn(new Validation(1, 0, 0, rowValidationList));

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/validate/123456789")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // then
        Assert.assertEquals(response.getStatus(), (HttpStatus.OK.value());
        Assert(response.getContentAsString(), validationJacksonTester.write(new Validation("Rob", "Mannon", "RobotMan")).getJson());
    }
}
{"id":"b16010a6-b943-46b9-b625-98ec5bedc0c4","totalLines":0,"validLines":2,"phoneNumberList":[{"id":"","phoneNumber":"278111","validationResult":"correct"},{"id":"","phoneNumber":"278111ss","validationResult":"correct"}]}