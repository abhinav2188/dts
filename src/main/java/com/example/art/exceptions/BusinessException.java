package com.example.art.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class BusinessException extends UserRequestException{

    private Map<String,String> errors;

    public BusinessException(String customMsg, Map<String,String> errors){
        super(customMsg);
        this.errors = errors;
    }

    public BusinessException(String msg){
        super(msg);
    }

    public BusinessException(){
        super();
    }

}
