package com.farmdigital.nerddevs.Exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoJWtException  extends  RuntimeException{
private  String  message;
private  int statusCode;

    public NoJWtException( String message, int statusCode) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }
}
