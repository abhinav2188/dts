package com.example.art.utils;

public class StringUtils {

    public String generateUpdateMsg(String param, Object oldVal, Object newVal) {
        return  param + " : '" + printVal(oldVal) + "' -> '" + printVal(newVal)+"'";
    }

    public String printVal(Object val){
        return val == null ? "null" : val.toString();
    }

}
