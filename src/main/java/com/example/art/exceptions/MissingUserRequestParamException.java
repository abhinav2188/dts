package com.example.art.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class MissingUserRequestParamException extends UserRequestException {

    private List<String> params;

    public MissingUserRequestParamException(List<String> params){
        super("Missing parameters: "+ params);
        this.params = params;
    }

    public MissingUserRequestParamException(String customMsg, List<String> params){
        super(customMsg + ": " + params);
        this.params = params;
    }

    public MissingUserRequestParamException(String customMsg){
        super(customMsg);
    }

}
