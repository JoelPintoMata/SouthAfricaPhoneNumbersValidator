//package com.example.codechallenge.controllers;
//
//import com.example.codechallenge.Main;
//import org.apache.http.entity.ContentType;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Main.class,
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource(locations = "classpath:/application-test.properties")
//public class MainControllerTests {
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    /**
//     * Tests all application controllers
//     * 1) Availability?
//     * 2) Return a Json?
//     */
//    @Test
//    public void test() {
//        ResponseEntity<String> forEntity = this.restTemplate.getForEntity("/",
//                String.class);
//
//        Assert.assertEquals(forEntity.getStatusCode().value(), HttpStatus.OK.value());
//        Assert.assertEquals(forEntity.getHeaders().getContentType().toString(), ContentType.APPLICATION_JSON.toString().replace(" ", ""));
//
//        forEntity = this.restTemplate.getForEntity("/0",
//                String.class);
//
//        Assert.assertEquals(forEntity.getStatusCode().value(), HttpStatus.OK.value());
//        Assert.assertEquals(forEntity.getHeaders().getContentType().toString(), ContentType.APPLICATION_JSON.toString().replace(" ", ""));
//
//        forEntity = this.restTemplate.getForEntity("/1",
//                String.class);
//
//        Assert.assertEquals(forEntity.getStatusCode().value(), HttpStatus.OK.value());
//        Assert.assertEquals(forEntity.getHeaders().getContentType().toString(), ContentType.APPLICATION_JSON.toString().replace(" ", ""));
//
//        forEntity = this.restTemplate.getForEntity("/2",
//                String.class);
//
//        Assert.assertEquals(forEntity.getStatusCode().value(), HttpStatus.OK.value());
//        Assert.assertEquals(forEntity.getHeaders().getContentType().toString(), ContentType.APPLICATION_JSON.toString().replace(" ", ""));
//
//        forEntity = this.restTemplate.getForEntity("/3",
//                String.class);
//
//        Assert.assertEquals(forEntity.getStatusCode().value(), HttpStatus.OK.value());
//        Assert.assertEquals(forEntity.getHeaders().getContentType().toString(), ContentType.APPLICATION_JSON.toString().replace(" ", ""));
//
//        forEntity = this.restTemplate.getForEntity("/4",
//                String.class);
//
//        Assert.assertEquals(forEntity.getStatusCode().value(), HttpStatus.OK.value());
//        Assert.assertEquals(forEntity.getHeaders().getContentType().toString(), ContentType.APPLICATION_JSON.toString().replace(" ", ""));
//
//        forEntity = this.restTemplate.getForEntity("/5",
//                String.class);
//
//        Assert.assertEquals(forEntity.getStatusCode().value(), HttpStatus.OK.value());
//        Assert.assertEquals(forEntity.getHeaders().getContentType().toString(), ContentType.APPLICATION_JSON.toString().replace(" ", ""));
//
//        forEntity = this.restTemplate.getForEntity("/6",
//                String.class);
//
//        Assert.assertEquals(forEntity.getStatusCode().value(), HttpStatus.OK.value());
//        Assert.assertEquals(forEntity.getHeaders().getContentType().toString(), ContentType.APPLICATION_JSON.toString().replace(" ", ""));
//    }
//}
