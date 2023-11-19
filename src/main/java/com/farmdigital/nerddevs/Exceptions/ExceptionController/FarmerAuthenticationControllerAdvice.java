package com.farmdigital.nerddevs.Exceptions.ExceptionController;

import com.farmdigital.nerddevs.Exceptions.UserAlreadyExistException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class FarmerAuthenticationControllerAdvice {
    private final Map<String, String> errorMessage = new HashMap<>();

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleAlreadyExistingUser(UserAlreadyExistException ex) {
        errorMessage.put("errorMessage", ex.getMessage());
        return errorMessage;


    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
//    ! handle malformed jwt signature
    public ProblemDetail handleMalformedJwtSignature(EntityNotFoundException ex) {
        errorMessage.put("errorMessage", ex.getMessage());
        errorMessage.put("statusCode",HttpStatus.UNAUTHORIZED.toString());
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problemDetail.setProperty("error", errorMessage);
        return problemDetail;


    }

//    ! handle invalid request for the  registration
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String,String > invalidArguments(MethodArgumentNotValidException ex){
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error->errorMessage.put(error.getField(),error.getDefaultMessage()));
        return  errorMessage;

    }

}
