package com.example.art.exceptions;

public class UserNotFoundException extends Exception {

    String param;
    Object value;

    public UserNotFoundException(String param, Object value){
        super("User not found with "+param+" value= "+value);
        this.param = param;
        this.value = value;
    }
}
