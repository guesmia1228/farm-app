package com.farmdigital.nerddevs.controller;

import com.farmdigital.nerddevs.Exceptions.InvalidAuthenticationException;
import com.farmdigital.nerddevs.service.AccountVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/agri_connect/verify")
public class AccountVerificationController {
    private  final AccountVerificationService verificationService;
    @GetMapping("/account_verification")
    public ResponseEntity<?> verifyUserAccount(@RequestParam("token")String  token) throws InvalidAuthenticationException{
//         ! if token is invalid
        if(token==null){
            throw new InvalidAuthenticationException("invalid access token");
        }
//    !   extract the email from the token
        var message=verificationService.verifyUserAccount(token);
        URI redirect= URI.create("/login");
        Map<String,String > respose= new HashMap<>();
        respose.put("message",message);
        respose.put("STATUS_CODE", HttpStatus.CREATED.toString());
       return  ResponseEntity.created(redirect).body(respose);
    }
}
