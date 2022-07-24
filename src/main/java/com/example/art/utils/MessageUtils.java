package com.example.art.utils;

public class MessageUtils {

    public static String successMultipleDealResponse(int count){
        return "fetched " + count + " deals from database";
    }

    public static String noAuthorization(String entity){
        return "User is not authorized to view/update this "+entity;
    }

    public static String successGetMessage(String entity){
        return "Successfully fetched "+entity;
    }

}
