package com.example.art.exceptions;

public class EntityNotFoundException extends Exception {

    String entity;
    String param;
    Object value;

    public EntityNotFoundException(String entity,String param, Object value){
        super(entity+" not found with "+param+" value= "+value);
        this.entity = entity;
        this.param = param;
        this.value = value;
    }

}
