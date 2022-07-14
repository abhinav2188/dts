package com.example.art.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestException extends Exception {

    private String customMsg;

    public UserRequestException(){
    }

    public UserRequestException(String customMsg) {
        this.customMsg = customMsg;
    }

}
