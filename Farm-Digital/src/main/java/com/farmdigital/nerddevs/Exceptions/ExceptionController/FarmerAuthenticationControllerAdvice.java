package com.farmdigital.nerddevs.Exceptions.ExceptionController;

import com.farmdigital.nerddevs.Exceptions.UserAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class FarmerAuthenticationControllerAdvice {
    private final Map<String ,String > errorMessage= new HashMap<>();
    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String,String> handleAlreadyExistingUser(UserAlreadyExistException ex){
errorMessage.put("errorMessage",ex.getMessage());
return  errorMessage;


    }
}
