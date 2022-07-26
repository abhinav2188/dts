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

    public static String successPostMessage(String entity) {
        return "Successfully created "+entity;
    }

    public static String successGetMessage(String entity, int count){
        return "Successfully fetched " + count + " " + entity + " from database";
    }

    public static String successDeleteMessage(String entity) {
        return "Successfully deleted " + entity;
    }

    public static String successUpdateMessage(String entity) {
        return "Successfully updated "+entity;
    }

    public static String failureFileStorage(String fileName) {
        return "Failure uploading file '"+ fileName+ "'";
    }
}
