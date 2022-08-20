package com.example.art.utils;

import org.springframework.stereotype.Component;

@Component
public class StringUtils {

    public String generateUpdateMsg(String param, Object oldVal, Object newVal) {
        return  param + " : '" + printVal(oldVal) + "' -> '" + printVal(newVal)+"'";
    }

    public String printVal(Object val){
        return val == null ? "null" : val.toString();
    }

    public boolean isUpdateable(Object oldVal, Object newVal){
        return newVal!= null  && newVal!="" && !newVal.equals(oldVal);
    }

}
