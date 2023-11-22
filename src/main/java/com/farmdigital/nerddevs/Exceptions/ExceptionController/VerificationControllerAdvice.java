package com.farmdigital.nerddevs.Exceptions.ExceptionController;

import com.farmdigital.nerddevs.Exceptions.InvalidAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class VerificationControllerAdvice {

    private  final Map<String ,String> errorMap= new HashMap<>();
    @ExceptionHandler(InvalidAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String ,String > unAuthorizedAcess( InvalidAuthenticationException ex){
        errorMap.put("errorMessage",ex.getMessage());
        errorMap.put("errorType","UNAUTHORIZED_REQUEST");
        return  errorMap;

    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public  Map<String ,String > handleNOTokenProvided(MissingServletRequestParameterException ex){

        errorMap.put("errorMessage",ex.getMessage());
        errorMap.put("errorType","FORBIDDEN REQUEST");
        return  errorMap;

    }
}
