package com.farmdigital.nerddevs.Exceptions.ExceptionController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExceptionResponse {
    private Map<String ,Object> errorMessage= new HashMap<>();

    public  Map<String,Object> setErrorResponse(String message,String statusCode,String errorType){

        errorMessage.put("errorMessage",message);
        errorMessage.put("statusCode",statusCode);
        errorMessage.put("errorType",errorType);
        return  errorMessage;
    }

}
