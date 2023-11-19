package com.farmdigital.nerddevs.Exceptions;

public class UserAlreadyExistException  extends RuntimeException{
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
