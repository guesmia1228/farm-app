package com.farmdigital.nerddevs.controller;

import com.farmdigital.nerddevs.Dto.AuthenticationDto;
import com.farmdigital.nerddevs.Dto.FarmerRegistrationDto;
import com.farmdigital.nerddevs.Exceptions.ExceptionController.FarmerAuthenticationControllerAdvice;
import com.farmdigital.nerddevs.service.UserRegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.net.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc

@SpringBootTest(classes = FarmerAuthenticationControllerTest.class)
@ExtendWith(SpringExtension.class)
public class FarmerAuthenticationControllerTest {

    @Autowired
    MockMvc mockMvc;
    String baseUrl;
    FarmerRegistrationDto newFarmer;
    Map<String, String> response = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();
    @Mock
    private UserRegistrationService userRegistrationService;
    @InjectMocks
    private FarmerAuthenticationController farmerAuthenticationController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(farmerAuthenticationController)
                .setControllerAdvice(new FarmerAuthenticationControllerAdvice())
                .build();
        baseUrl = "/api/v1/farm_digital";
        newFarmer = FarmerRegistrationDto.builder()
                .name("anericokakai")
                .email("anericokakai@gmail.com")
                .password("Anericokakai@2004")
                .phoneNumber("792626899")
                .build();
    }

    @Test
    @Order(1)
    public void assert_willCreateAUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();


        response.put("message", "user created successfully");

        String request = objectMapper.writeValueAsString(newFarmer);
        Mockito.when(userRegistrationService.saveUer(newFarmer)).thenReturn(response);
        mockMvc.perform(post(baseUrl + "/super/user/register")
                        .contentType("application/json")
                        .characterEncoding(StandardCharsets.UTF_8).content(request))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @Order(2)
    public void assert_willThrowExeption_Invalid_Inputs() throws Exception {

        FarmerRegistrationDto newfarmer = FarmerRegistrationDto
                .builder()
                .build();


        String request = mapper.writeValueAsString(newfarmer);
        mockMvc.perform(post(baseUrl + "/super/user/register")
                        .content(request).characterEncoding("utf-8")
                        .contentType("application/json"))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof MethodArgumentNotValidException))
                .andExpect(jsonPath("$.phoneNumber").value("phone number is a requirement in order to continue with your registarion"))
                .andExpect(jsonPath("$.name").value("name cannot be empty"))
                .andDo(print());
    }

    @Test
    @Order(3)
    public void assert_WillThrowAuthentication_invalid_inputs() throws Exception {


        AuthenticationDto authenticationDto = AuthenticationDto.builder().build();
        String request = mapper.writeValueAsString(authenticationDto);

        mockMvc.perform(post(baseUrl + "/super/user/authenticate")
                        .contentType("application/json")
                        .characterEncoding("utf-8")
                        .content(request))

                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.email").value("email field cannot be blank ! "))
                .andExpect(jsonPath("$.password").value("password field cannot be blank !"))
                .andDo(print());


    }

    @Test
    @Order(4)
    public void assert_will_AuthenicateUser() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbmVyaWNva2FrYWlAZ21haWwuY29tIiwibmFtZSI6ImFuZXJpY29rYWthaUBnbWFpbC5jb20iLCJpYXQiOjE1MTYyMzkwMjJ9.di54c7dhhSJu3nT9fFNmvQpvZncJQIy2nSTcrqoBOIk";
        AuthenticationDto authenticationDto = AuthenticationDto.builder()
                .email("anericokakai@gmail.com").password("anerico").build();
        String request = mapper.writeValueAsString(authenticationDto);
        Mockito.when(userRegistrationService.authenticateauser(authenticationDto)).thenReturn(token);
        mockMvc.perform(post(baseUrl + "/super/user/authenticate")
                        .content(request).contentType("application/json"))
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(5)
    public void assertWillThrowBlankEmailException() throws Exception {
        String request = mapper.writeValueAsString("");
        mockMvc.perform(post(baseUrl + "/super/user/reset_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)).andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.email").value("email cannot be blank"))
                .andDo(print());

    }



}
