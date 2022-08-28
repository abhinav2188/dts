package com.example.art.exceptions;

public class InvalidFieldException extends Exception{

    private String param;

    public InvalidFieldException(String param) {
        super("invalid field: "+param);
        this.param = param;
    }
}
