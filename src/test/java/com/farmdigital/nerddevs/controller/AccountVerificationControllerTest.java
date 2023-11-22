package com.farmdigital.nerddevs.controller;

import com.farmdigital.nerddevs.Exceptions.ExceptionController.FarmerAuthenticationControllerAdvice;
import com.farmdigital.nerddevs.Exceptions.ExceptionController.VerificationControllerAdvice;
import com.farmdigital.nerddevs.Exceptions.InvalidAuthenticationException;
import com.farmdigital.nerddevs.service.AccountVerificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MissingServletRequestParameterException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = AccountVerificationControllerTest.class)
public class AccountVerificationControllerTest {

    String baseUrl = "/api/v1/agri_connect/verify";
    private MockMvc mockMvc;
    @Mock
    private AccountVerificationService accountVerificationService;
    @InjectMocks
    private AccountVerificationController accountVerificationController;
    String token;
    @BeforeEach
    void setUp() {
      token = "eyJhbGciOiJIUzI1NiJ9" +
                ".eyJzdWIiOiJhbmVyaWNva2FrYWlAZ21haWwuY29" +
                "tIiwiaWF0IjoxNzAwNjU1NzA0LCJleHAiOjE3MDA2NjExMDR9" +
                ".1_JFMNcud5HLBF4xSqK76ACxwg-KJCCBmPMZVpi8Mxc";

        mockMvc = MockMvcBuilders.standaloneSetup(accountVerificationController)
                .setControllerAdvice(new VerificationControllerAdvice()).build();

    }


    @Test
    public void assert_willReturn_responseStatusCreated() throws InvalidAuthenticationException, Exception {

        String expectedRespose = "account verification success";
        String expectedStatusCode = "201 CREATED";
        Mockito.when(accountVerificationService.verifyUserAccount(token)
        ).thenReturn("account verification success");
        mockMvc.perform(get(baseUrl + "/account_verification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("token", token)
                        .characterEncoding("utf-8"))
                .andExpect(jsonPath("$.message").value(expectedRespose))
                .andExpect(redirectedUrl("/login"))
                .andExpect(jsonPath("$.STATUS_CODE").value(expectedStatusCode))

                .andDo(print());

    }

@Test
    public  void assert_will_throw_Exception_forInvalidToken() throws Exception{
        String  expectedError="invalid access token , your token might have expired ";

        Mockito.when(accountVerificationService.verifyUserAccount(token)).thenThrow(new InvalidAuthenticationException("invalid access token , your token might have expired "));

        mockMvc.perform(get(baseUrl+"/account_verification")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                        .param("token",token))
                .andExpect(result -> assertTrue(result.getResolvedException()
                instanceof InvalidAuthenticationException))
//                .andExpect(jsonPath("$.errorMessage").value(expectedError))
                .andDo(print());

}
}
